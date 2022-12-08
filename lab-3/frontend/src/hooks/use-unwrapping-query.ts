import { QueryKey, useQuery, UseQueryOptions } from '@tanstack/react-query';
import { AxiosError, AxiosResponse } from 'axios';

import processServerError from 'utils/server-error';

const useUnwrappingQuery = <Data>(
  queryKey: QueryKey,
  queryFn: () => Promise<AxiosResponse<Data>>,
  options?: UseQueryOptions<Data, AxiosError>
) =>
  useQuery(queryKey, () => queryFn().then(response => response.data), {
    onError: processServerError,
    ...options,
  });

export default useUnwrappingQuery;
