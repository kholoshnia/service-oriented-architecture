import { ColumnsType } from 'antd/es/table';
import { SorterResult } from 'antd/lib/table/interface';

const antdToPage = {
  ascend: 'asc',
  descend: 'desc',
};

export const getSortParams = <Data>(
  sortResult: SorterResult<Data> | SorterResult<Data>[]
) =>
  (Array.isArray(sortResult) ? sortResult : [sortResult])
    .map(s => (s.order ? `${s.columnKey},${antdToPage[s.order]}` : ''))
    .filter(s => s !== '');

const pageToAntd = {
  asc: 'ascend',
  desc: 'descend',
};

export const setSortParams = <Data>(
  columns: ColumnsType<Data>,
  sort?: string[]
) => {
  if (!sort) return columns;

  const sortDirections: any = sort.reduce((acc: any, cur) => {
    const [key, value] = cur.split(',');
    acc[key] = pageToAntd[value];
    return acc;
  }, {});

  return columns.map(column => {
    const order = sortDirections[column.key as string] as any;
    if (order) column.defaultSortOrder = order;
    return column;
  });
};
