import { FC } from 'react';

import { Table } from 'antd';
import type { ColumnsType } from 'antd/es/table';

import { Product } from 'models/product';
import { PaginationParams } from 'models/utils';
import { addSort, getSort } from 'utils/get-sort';

type ProductTableProps = {
  products?: Product[];
  total: number;
  pagination: PaginationParams;
  onPagination: (pagination: PaginationParams) => void;
  loading?: boolean;
};

const columns: ColumnsType<Product> = [
  {
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    sorter: { multiple: 1 },
  },
  {
    title: 'Creation date',
    dataIndex: 'creationDate',
    key: 'creationDate',
    sorter: { multiple: 1 },
  },
  {
    title: 'Name',
    dataIndex: 'name',
    key: 'name',
    sorter: { multiple: 1 },
  },
  // {
  //   title: 'Coordinates',
  //   dataIndex: 'coordinates',
  //   key: 'coordinates',
  // },
  {
    title: 'Price',
    dataIndex: 'price',
    key: 'price',
    sorter: { multiple: 1 },
  },
  // {
  //   title: 'Part number',
  //   dataIndex: 'partNumber',
  //   key: 'partNumber',
  // },
  {
    title: 'Manufacture cost',
    dataIndex: 'manufactureCost',
    key: 'manufactureCost',
    sorter: { multiple: 1 },
  },
  {
    title: 'Unit of measure',
    dataIndex: 'unitOfMeasure',
    key: 'unitOfMeasure',
    sorter: { multiple: 1 },
  },
  // {
  //   title: 'Manufacturer',
  //   dataIndex: 'manufacturer',
  //   key: 'manufacturer',
  // },
];

const ProductTable: FC<ProductTableProps> = ({
  products = [],
  total,
  loading,
  pagination,
  onPagination,
}) => {
  return (
    <Table
      columns={addSort(columns, pagination.sort)}
      dataSource={products.map((p: any) => {
        p.key = p.id;
        return p;
      })}
      onChange={(_pagination, _, sort) =>
        onPagination({ ...pagination, sort: getSort(sort) })
      }
      loading={loading}
      pagination={{
        total,
        defaultCurrent: pagination.page,
        pageSize: pagination.size,
        pageSizeOptions: [5, 10, 20],
        defaultPageSize: 5,
        showSizeChanger: true,
        onChange: (page, size) => onPagination({ ...pagination, page, size }),
      }}
    />
  );
};

export default ProductTable;
