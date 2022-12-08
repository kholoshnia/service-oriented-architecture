import { useState } from 'react';

import { useQuery, UseQueryOptions } from '@tanstack/react-query';
import { AxiosError, AxiosResponse } from 'axios';

import useQueryPagination from 'hooks/use-query-pagination';
import { Page, PaginationParams } from 'models/utils';
import processServerError from 'utils/server-error';

const usePaginatedQuery = <Item>(
  prefix: string,
  request: (pagination: PaginationParams) => Promise<AxiosResponse<Page<Item>>>,
  options?: UseQueryOptions<Item[], AxiosError> & {
    deps?: unknown[];
    pagination?: PaginationParams;
  }
) => {
  const { pagination, setPagination } = useQueryPagination(
    prefix,
    options?.pagination
  );

  const [total, setTotal] = useState<number>(0);

  const query = useQuery(
    [prefix, ...(options?.deps ?? []), pagination],
    () =>
      request(pagination).then(response => {
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
