import { OrganizationId } from 'models/organization';
import { Product, UnitOfMeasure } from 'models/product';
import { Page, PaginationParams } from 'models/utils';
import { shopClient } from 'utils/axios-clients';

const filtersApi = {
  getProductsByManufacturer: (
    manufacturerId: OrganizationId,
    paginationParams?: PaginationParams
  ) =>
    shopClient.get<Page<Product>>(`/filter/manufacturer/${manufacturerId}`, {
      params: paginationParams,
    }),

  getProductsByUnitOfMeasure: (
    unitOfMeasure: UnitOfMeasure,
    paginationParams?: PaginationParams
  ) =>
    shopClient.get<Page<Product>>(`/filter/unit-of-measure/${unitOfMeasure}`, {
      params: paginationParams,
    }),
};

export default filtersApi;
