import {
  ManufactureCostGroupDto,
  NewProduct,
  Product,
  ProductId,
} from 'models/product';
import { Page, PaginationParams } from 'models/utils';
import { storageClient } from 'utils/axios-clients';

const storageApi = {
  getProductById: (productId: ProductId) =>
    storageClient.get<Product>(`/products/${productId}`),

  updateProduct: (productId: ProductId, newProduct: NewProduct) =>
    storageClient.put<Product>(`/products/${productId}`, newProduct),

  deleteProduct: (productId: ProductId) =>
    storageClient.delete(`/products/${productId}`),

  getProducts: (paginationParams?: PaginationParams) =>
    storageClient.get<Page<Product>>('/products', { params: paginationParams }),

  addProduct: (newProduct: NewProduct) =>
    storageClient.post<Product>('/products', newProduct),

  addProductWithMaxPrice: () =>
    storageClient.get<Product>('/products/max-price'),

  getManufactureCostGroups: (paginationParams?: PaginationParams) =>
    storageClient.get<Page<ManufactureCostGroupDto>>('/products/max-price', {
      params: paginationParams,
    }),

  getProductsWithGreaterPartNumber: (
    partNumber: string,
    paginationParams?: PaginationParams
  ) =>
    storageClient.get<Page<ManufactureCostGroupDto>>(
      '/products/greater-part-number',
      { params: { ...paginationParams, partNumber } }
    ),
};

export default storageApi;
