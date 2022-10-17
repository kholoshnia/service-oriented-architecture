import axios, { AxiosRequestConfig, AxiosResponse, CustomParamsSerializer } from 'axios';
import { parseXml, toXml } from 'utils/xml-helpers';
import qs from 'qs'

const serializeParams: CustomParamsSerializer = (params) => qs.stringify(params, {
  arrayFormat: 'repeat'
})

const onRequest = async (config: AxiosRequestConfig) => {
  config.data = toXml(config.data);

  config.headers = {
    ...config.headers,
    'Content-Type': 'application/xml'
  }

  return config;
};

const onResponse = async (response: AxiosResponse) => {
  response.data = parseXml(response.data);
  return response
}

const createAxiosClient = (config?: AxiosRequestConfig) => {
  const client = axios.create({ ...config, paramsSerializer: { serialize: serializeParams } });
  client.interceptors.request.use(onRequest);
  client.interceptors.response.use(onResponse);
  return client;
};

export default createAxiosClient;

