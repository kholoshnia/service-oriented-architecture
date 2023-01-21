import { FC, useMemo } from 'react';

import { Table } from 'antd';
import { ColumnsType } from 'antd/es/table';

import { ManufactureCostGroup } from 'models/product';
import { PaginationParams } from 'models/utils';
import setIdKeys from 'utils/id-keys';
import { getSortParams, setSortParams } from 'utils/sort-helpers';

const groupColumns: ColumnsType<ManufactureCostGroup> = [
  {
    title: 'Manufacture cost',
    dataIndex: 'manufactureCost',
    key: 'manufactureCost',
  },
  {
    title: 'Count',
    dataIndex: 'count',
    key: 'count',
  },
];

type GroupsTableProps = {
  groups?: ManufactureCostGroup[];
  loading?: boolean;
  total: number;
  pagination: PaginationParams;
  onPagination: (pagination: PaginationParams) => void;
};

const GroupsTable: FC<GroupsTableProps> = ({
  groups = [],
  loading,
  total,
  pagination,
  onPagination,
}) => {
  const dataSource = useMemo(
    () => setIdKeys(groups, group => group.manufactureCost),
    [groups]
  );

  const onSortChange = sort =>
    onPagination({ ...pagination, sort: getSortParams(sort) });

  return (
    <Table
      bordered
      size="middle"
      columns={setSortParams(groupColumns, pagination.sort)}
      dataSource={dataSource}
      loading={loading}
      pagination={{
        total,
        current: pagination.page ?? 1,
        pageSize: pagination.size ?? 5,
        showSizeChanger: true,
        pageSizeOptions: [5, 10, 20],
        defaultPageSize: 5,
        onChange: (page, size) => onPagination({ ...pagination, page, size }),
      }}
      onChange={(pagination, filters, sort) => onSortChange(sort)}
    />
  );
};

export default GroupsTable;
