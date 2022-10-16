import { AxiosResponse } from 'axios';

import { Page } from 'models/utils';

const getEmptyPage = <Data>() =>
  ({
    data: {
      page: 1,
      size: 0,
      total: 0,
      data: [],
    } as Page<Data>,
  } as AxiosResponse<Page<Data>>);

export default getEmptyPage;
