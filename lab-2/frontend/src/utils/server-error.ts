import { notification } from 'antd';
import { AxiosError } from 'axios';

export const getErrorMessage = (e: AxiosError): string => {
  if ((e.response?.data as any)?.error) return (e.response?.data as any)?.error;
  if (e.response?.data) return String(e.response.data);
  if (e.message) return e.message;
  return 'Request error.';
};

export const showError = (e: AxiosError, title = 'Server error!') =>
  notification['error']({
    message: title,
    description: getErrorMessage(e),
  });

const processServerError = (e: AxiosError) => {
  if (e.code === 'ERR_NETWORK') showError(e, 'Offline!');
  else if (e.response && e.response.status >= 500) showError(e);
};

export default processServerError;
