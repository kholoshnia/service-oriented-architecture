import { FC, useMemo } from 'react';

import { Table, TablePaginationConfig, Tooltip } from 'antd';
import type { ColumnsType } from 'antd/es/table';

import { Product } from 'models/product';
import { PaginationParams } from 'models/utils';
import setIdKeys from 'utils/id-keys';
import {
  formatDate,
  getDateOnly,
  ProductColumns,
  productsToColumns,
} from 'utils/product-helpers';
import { setSortParams, getSortParams } from 'utils/sort-helpers';
import { shortenString } from 'utils/string-helpers';

type ProductTableProps = {
  products?: Product[];
  loading?: boolean;
  total: number;
  pagination: PaginationParams;
  onPagination: (pagination: PaginationParams) => void;
};

const columns: ColumnsType<ProductColumns> = [
  {
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    sorter: { multiple: 1 },
    fixed: 'left',
  },
  {
    title: 'Name',
    dataIndex: 'name',
    key: 'name',
    sorter: { multiple: 1 },
    fixed: 'left',
  },
  {
    title: 'Created',
    dataIndex: 'creationDate',
    key: 'creationDate',
    sorter: { multiple: 1 },
    render: creationDate => (
      <Tooltip title={formatDate(creationDate)}>
        {getDateOnly(creationDate)}
      </Tooltip>
    ),
  },
  {
    title: 'Coordinates',
    children: [
      {
        title: 'X',
        dataIndex: 'coordinatesX',
        key: 'coordinates.x',
        sorter: { multiple: 1 },
      },
      {
        title: 'Y',
        dataIndex: 'coordinatesY',
        key: 'coordinates.y',
        sorter: { multiple: 1 },
      },
    ],
  },
  {
    title: 'Price',
    dataIndex: 'price',
    key: 'price',
    sorter: { multiple: 1 },
  },
  {
    title: 'Part number',
    dataIndex: 'partNumber',
    key: 'partNumber',
    sorter: { multiple: 1 },
    render: partNumber => (
      <Tooltip title={partNumber}>{shortenString(partNumber, 15)}</Tooltip>
    ),
  },
  {
    title: 'Cost',
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
  {
    title: 'Manufacturer',
    children: [
      {
        title: 'ID',
        dataIndex: 'manufacturerId',
        key: 'manufacturer.id',
        sorter: { multiple: 1 },
      },
      {
        title: 'Name',
        dataIndex: 'manufacturerName',
        key: 'manufacturer.name',
        sorter: { multiple: 1 },
      },
      {
        title: 'Full name',
        dataIndex: 'manufacturerFullName',
        key: 'manufacturer.fullName',
        sorter: { multiple: 1 },
      },
      {
        title: 'Annual turnover',
        dataIndex: 'manufacturerAnnualTurnover',
        key: 'manufacturer.annualTurnover',
        sorter: { multiple: 1 },
      },
      {
        title: 'Employees count',
        dataIndex: 'manufacturerEmployeesCount',
        key: 'manufacturer.employeesCount',
        sorter: { multiple: 1 },
      },
    ],
  },
];

const ProductTable: FC<ProductTableProps> = ({
  products = [],
  loading,
  total,
  pagination,
  onPagination,
}) => {
  const dataSource = useMemo(
    () => setIdKeys(productsToColumns(products)),
    [products]
  );

  const paginationParams: TablePaginationConfig = {
    total,
    defaultCurrent: pagination.page,
    pageSize: pagination.size,
    pageSizeOptions: [5, 10, 20],
    defaultPageSize: 5,
    showSizeChanger: true,
    onChange: (page, size) => onPagination({ ...pagination, page, size }),
  };

  const onSortChange = sort =>
    onPagination({ ...pagination, sort: getSortParams(sort) });

  return (
    <Table
      bordered
      size="middle"
      scroll={{ x: true }}
      columns={setSortParams(columns, pagination.sort)}
      dataSource={dataSource}
      loading={loading}
      pagination={paginationParams}
      onChange={(_pagination, _, sort) => onSortChange(sort)}
    />
  );
};

export default ProductTable;
