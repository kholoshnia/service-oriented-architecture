import { FilterValue } from 'antd/lib/table/interface';

export const getFilterParams = <Data>(
  filters?: Record<string, FilterValue | null>
): Data | undefined => {
  if (!filters) return undefined;
  return Object.keys(filters).reduce((acc, filter) => {
    const value = filters[filter];
    if (value) acc[filter] = value[0];
    return acc;
  }, {}) as Data;
};
