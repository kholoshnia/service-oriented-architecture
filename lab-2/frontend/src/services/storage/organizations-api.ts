import { Organization, OrganizationFilters } from 'models/organization';
import { Page, PaginationParams } from 'models/utils';
import { storageClient } from 'utils/axios-clients';

const organizationsApi = {
  getOrganizations: (
    paginationParams?: PaginationParams,
    filterParams?: OrganizationFilters
  ) =>
    storageClient.get<Page<Organization>>('/organizations', {
      params: { ...paginationParams, ...filterParams },
    }),
};

export default organizationsApi;
