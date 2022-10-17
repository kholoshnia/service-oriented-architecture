import {
  ManufactureCostGroup,
  NewProduct,
  Product,
  ProductId,
} from 'models/product';
import { Page, PaginationParams } from 'models/utils';
import { storageClient } from 'utils/axios-clients';
import { ProductColumns } from 'utils/product-helpers';

const storageApi = {
  getProductById: (productId: ProductId) =>
    storageClient.get<Product>(`/products/${productId}`),

  updateProduct: (productId: ProductId, newProduct: NewProduct) =>
    storageClient.put<Product>(`/products/${productId}`, newProduct),

  deleteProduct: (productId: ProductId) =>
    storageClient.delete(`/products/${productId}`),

  getProducts: (
    paginationParams?: PaginationParams,
    filterParams?: ProductColumns
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
};

export default storageApi;
