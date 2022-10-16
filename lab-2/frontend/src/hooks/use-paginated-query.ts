import { useState } from 'react';

import { useQuery, UseQueryOptions } from '@tanstack/react-query';
import { notification } from 'antd';
import { AxiosError, AxiosResponse } from 'axios';
import { ArrayParam, NumberParam, useQueryParams } from 'use-query-params';

import { Page, PaginationParams } from 'models/utils';

export const processServerError = (e: AxiosError) => {
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

const usePaginatedQuery = <Item>(
  queryKey: any,
  queryFn: (pagination: PaginationParams) => Promise<AxiosResponse<Page<Item>>>,
  options?: UseQueryOptions<Item[], AxiosError>
) => {
  const [paginationQuery, setPaginationQuery] = useQueryParams({
    page: NumberParam,
    size: NumberParam,
    sort: ArrayParam,
  });

  const pagination = paginationQuery as PaginationParams;
  const setPagination = (pagination: PaginationParams) =>
    setPaginationQuery(pagination);

  const [total, setTotal] = useState<number>(0);

  const query = useQuery(
    [...queryKey, pagination],
    () =>
      queryFn(pagination).then(response => {
        const data = response.data;
        setTotal(data.total);
        return data.data;
      }),
    {
      onError: (e: AxiosError) => processServerError(e),
      ...options,
    } as any
  );

  return {
    ...query,
    pagination,
    setPagination,
    total,
  };
};

export default usePaginatedQuery;
