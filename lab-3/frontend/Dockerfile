FROM node:18-alpine AS builder
ENV NODE_ENV production
WORKDIR /app

# cache dependencies
COPY package.json .
COPY yarn.lock .
COPY .yarn .yarn
COPY .yarnrc.yml .
RUN yarn install --frozen-lockfile

# build
COPY . .
ARG REACT_APP_STORAGE_API_URL
ARG REACT_APP_SHOP_API_URL
RUN yarn build

FROM nginx:1.21.0-alpine
ENV NODE_ENV production
COPY --from=builder /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf.template
ENTRYPOINT envsubst '$SSL_DOMAIN $SSL_CRT_PATH $SSL_KEY_PATH' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'
