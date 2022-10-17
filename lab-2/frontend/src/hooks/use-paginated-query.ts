import { useState } from 'react';

import { QueryKey, useQuery, UseQueryOptions } from '@tanstack/react-query';
import { AxiosError, AxiosResponse } from 'axios';
import { ArrayParam, NumberParam, useQueryParams } from 'use-query-params';

import { Page, PaginationParams } from 'models/utils';
import processServerError from 'utils/server-error';

const usePaginatedQuery = <Item>(
  queryKey: QueryKey,
  queryFn: (pagination: PaginationParams) => Promise<AxiosResponse<Page<Item>>>,
  options?: UseQueryOptions<Item[], AxiosError>
) => {
  const [paginationQuery, setPaginationQuery] = useQueryParams({
    page: NumberParam,
    size: NumberParam,
    sort: ArrayParam,
  });

  const pagination = paginationQuery as PaginationParams;
  const setPagination = (pagination: PaginationParams) => {
    setPaginationQuery(pagination);
  };

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
    total,
    pagination,
    setPagination,
  };
};

export default usePaginatedQuery;
