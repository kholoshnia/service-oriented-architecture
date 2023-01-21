import { OrganizationId } from 'models/organization';
import {
  ManufactureCostGroup,
  NewProduct,
  Product,
  ProductFilters,
  ProductId,
} from 'models/product';
import { Page, PaginationParams } from 'models/utils';
import { storageClient } from 'utils/axios-clients';

const productsApi = {
  getProductById: (productId: ProductId) =>
    storageClient.get<Product>(`/products/${productId}`),

  updateProduct: (productId: ProductId, newProduct: NewProduct) =>
    storageClient.put<Product>(`/products/${productId}`, newProduct),

  deleteProduct: (productId: ProductId) =>
    storageClient.delete(`/products/${productId}`),

  getProducts: (
    paginationParams?: PaginationParams,
    filterParams?: ProductFilters
  ) =>
    storageClient.get<Page<Product>>('/products', {
      params: { ...paginationParams, ...filterParams },
    }),

  addProduct: (newProduct: NewProduct) =>
    storageClient.post<Product>('/products', newProduct),

  getProductWithMaxPrice: () =>
    storageClient.get<Product>('/products/max-price'),

  getManufactureCostGroups: (paginationParams?: PaginationParams) =>
    storageClient.get<Page<ManufactureCostGroup>>(
      '/products/manufacture-cost-groups',
      { params: paginationParams }
    ),

  getProductsWithGreaterPartNumber: (
    partNumber: string,
    paginationParams?: PaginationParams
  ) =>
    storageClient.get<Page<Product>>('/products/greater-part-number', {
      params: { ...paginationParams, partNumber },
    }),

  getTransferProductsPage: (
    organizationId: OrganizationId,
    paginationParams?: PaginationParams,
    filterParams?: ProductFilters
  ) =>
    storageClient.get<Page<Product>>(`/products/${organizationId}/transfer`, {
      params: { ...paginationParams, ...filterParams },
    }),
};

export default productsApi;
