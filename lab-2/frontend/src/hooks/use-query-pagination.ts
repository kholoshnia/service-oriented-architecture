import { ArrayParam, NumberParam, useQueryParams } from 'use-query-params';

import { PaginationParams } from 'models/utils';

const useQueryPagination = (
  prefix: string,
  defaultPagination?: PaginationParams
) => {
  const pageParam = `${prefix}Page`;
  const sizeParam = `${prefix}Size`;
  const sortParam = `${prefix}Sort`;

  const [paginationQuery, setPaginationQuery] = useQueryParams({
    [pageParam]: NumberParam,
    [sizeParam]: NumberParam,
    [sortParam]: ArrayParam,
  });

  const pagination = {
    page: paginationQuery[pageParam],
    size: paginationQuery[sizeParam],
    sort: paginationQuery[sortParam],
    ...defaultPagination,
  } as PaginationParams;

  const setPagination = (pagination: PaginationParams) => {
    setPaginationQuery({
      [pageParam]: pagination.page,
      [sizeParam]: pagination.size,
      [sortParam]: pagination.sort,
    });
  };

  return {
    pagination,
    setPagination,
  };
};

export default useQueryPagination;
