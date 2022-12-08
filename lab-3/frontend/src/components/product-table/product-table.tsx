import { FC, useMemo } from 'react';

import { Table } from 'antd';

import useProductColumns from 'hooks/use-product-columns';
import { Product, ProductFilters, ProductId } from 'models/product';
import { PaginationParams } from 'models/utils';
import { getFilterParams } from 'utils/filter-helpers';
import setIdKeys from 'utils/id-keys';
import { productsToColumns } from 'utils/product-helpers';
import { getSortParams } from 'utils/sort-helpers';

export enum ProductTableActions {
  EDIT_DELETE,
  SELECT,
}

type ProductTableProps = {
  products?: Product[];
  loading?: boolean;
  total: number;
  pagination?: PaginationParams;
  onPagination?: (pagination: PaginationParams) => void;
  filters?: ProductFilters;
  onFilters?: (filters?: ProductFilters) => void;
  organizationFilter?: boolean;
  actions?: ProductTableActions;
  refetch?: () => void;
  fixName?: boolean;
  onSelect?: (productsId: ProductId, checked: boolean) => void;
  selected?: Set<ProductId>;
  sortable?: boolean;
};

const ProductTable: FC<ProductTableProps> = ({
  products = [],
  loading,
  total,
  pagination,
  onPagination,
  filters,
  onFilters,
  organizationFilter,
  actions,
  refetch,
  fixName,
  onSelect,
  selected,
  sortable = true,
}) => {
  const columns = useProductColumns({
    pagination,
    filters,
    onFilters,
    organizationFilter,
    actions,
    refetch,
    fixName,
    onSelect,
    selected,
    sortable,
  });

  const dataSource = useMemo(
    () => setIdKeys(productsToColumns(products)),
    [products]
  );

  const onFiltersChange = filters => onFilters?.(getFilterParams(filters));

  const onSortChange = sort =>
    onPagination?.({ ...pagination, sort: getSortParams(sort) });

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
        current: pagination?.page ?? 1,
        pageSize: pagination?.size ?? 5,
        showSizeChanger: true,
        pageSizeOptions: [5, 10, 20],
        defaultPageSize: 5,
        size: 'default',
        onChange: (page, size) => onPagination?.({ ...pagination, page, size }),
      }}
      onChange={(pagination, filters, sort) => {
        onFiltersChange(filters);
        onSortChange(sort);
      }}
    />
  );
};

export default ProductTable;
