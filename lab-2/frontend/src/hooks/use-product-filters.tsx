import { NumberParam, StringParam, useQueryParams } from 'use-query-params';

import { ProductColumns } from 'utils/product-helpers';

const useProductFilters = () => {
  const [filtersQuery, setFiltersQuest] = useQueryParams({
    id: NumberParam,
    creationDate: StringParam,
    name: StringParam,
    coordinatesX: NumberParam,
    coordinatesY: NumberParam,
    price: NumberParam,
    partNumber: StringParam,
    manufactureCost: NumberParam,
    unitOfMeasure: StringParam,
    manufacturerId: NumberParam,
    manufacturerName: StringParam,
    manufacturerFullName: StringParam,
    manufacturerAnnualTurnover: NumberParam,
    manufacturerEmployeesCount: NumberParam,
  });

  return {
    filters: filtersQuery as ProductColumns,
    setFilters: (filters?: ProductColumns) =>
      filters && setFiltersQuest(filters),
  };
};

export default useProductFilters;
