import { FC, useMemo } from 'react';

import { Table } from 'antd';

import useProductColumns from 'hooks/use-product-columns';
import { Product } from 'models/product';
import { PaginationParams } from 'models/utils';
import { getFilterParams } from 'utils/filter-helpers';
import setIdKeys from 'utils/id-keys';
import { ProductColumns, productsToColumns } from 'utils/product-helpers';
import { getSortParams } from 'utils/sort-helpers';

type ProductTableProps = {
  products?: Product[];
  loading?: boolean;
  total: number;
  pagination: PaginationParams;
  onPagination: (pagination: PaginationParams) => void;
  filters?: ProductColumns;
  onFilters?: (filters?: ProductColumns) => void;
  actions?: boolean;
  refetch?: () => void;
  editable?: boolean;
  fixName?: boolean;
};

const ProductTable: FC<ProductTableProps> = ({
  products = [],
  loading,
  total,
  pagination,
  onPagination,
  filters,
  onFilters,
  actions,
  refetch,
  fixName,
}) => {
  const columns = useProductColumns({
    pagination,
    filters,
    onFilters,
    actions,
    refetch,
    fixName,
  });

  const dataSource = useMemo(
    () => setIdKeys(productsToColumns(products)),
    [products]
  );

  const onFiltersChange = filters => onFilters?.(getFilterParams(filters));

  const onSortChange = sort =>
    onPagination({ ...pagination, sort: getSortParams(sort) });

  return (
    <Table
      bordered
      size="middle"
      scroll={{ x: true }}
      columns={columns}
      dataSource={dataSource}
      loading={loading}
      pagination={{
        total,
        current: pagination.page ?? 1,
        pageSize: pagination.size ?? 5,
        showSizeChanger: true,
        pageSizeOptions: [5, 10, 20],
        defaultPageSize: 5,
        size: 'default',
        onChange: (page, size) => onPagination({ ...pagination, page, size }),
      }}
      onChange={(pagination, filters, sort) => {
        onFiltersChange(filters);
        onSortChange(sort);
      }}
    />
  );
};

export default ProductTable;
