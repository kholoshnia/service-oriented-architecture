import { NumberParam, StringParam, useQueryParams } from 'use-query-params';

import { ProductFilters } from 'models/product';

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
    manufacturerCoordinatesX: NumberParam,
    manufacturerCoordinatesY: NumberParam,
  });

  return {
    filters: filtersQuery as ProductFilters,
    setFilters: (filters?: ProductFilters) =>
      filters && setFiltersQuest(filters),
  };
};

export default useProductFilters;
