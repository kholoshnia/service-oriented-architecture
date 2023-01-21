import createAxiosClient from 'config/axios-client';

export const shopClient = createAxiosClient({
  baseURL: process.env.REACT_APP_SHOP_API_URL,
});

export const storageClient = createAxiosClient({
  baseURL: process.env.REACT_APP_STORAGE_API_URL,
});
