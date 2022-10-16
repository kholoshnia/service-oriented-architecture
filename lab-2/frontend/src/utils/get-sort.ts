import { ColumnsType } from 'antd/es/table';
import { SorterResult } from 'antd/lib/table/interface';

const orderToShort = {
  ascend: 'asc',
  descend: 'desc',
};

const shortToOrder: any = {
  asc: 'ascend',
  desc: 'descend',
};

export const getSort = <Data>(
  sortResult: SorterResult<Data> | SorterResult<Data>[]
) =>
  (Array.isArray(sortResult) ? sortResult : [sortResult])
    .map(s => (s.order ? `${s.columnKey},${orderToShort[s.order]}` : ''))
    .filter(s => s !== '');

export const addSort = <Data>(columns: ColumnsType<Data>, sort?: string[]) => {
  if (!sort) return columns;

  const sortDirections: any = sort.reduce((acc: any, cur) => {
    const [key, value] = cur.split(',');
    acc[key] = shortToOrder[value];
    return acc;
  }, {});

  return columns.map(column => {
    const order = sortDirections[column.key as string] as any;
    if (order) column.defaultSortOrder = order;
    return column;
  });
};
