import { notification } from 'antd';
import { AxiosError } from 'axios';

const processServerError = (e: AxiosError) => {
  if (e.response && e.response.status >= 500) {
    notification['error']({
      message: 'Server error!',
      description:
        typeof e.response.data === 'object'
          ? (e.response.data as any).error
          : String(e.response.data),
    });
  }
};

export default processServerError;
