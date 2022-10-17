import { OrganizationId } from 'models/organization';
import { Product, ProductId, UnitOfMeasure } from 'models/product';
import { DateString } from 'models/utils';

export type ProductColumns = {
  id: ProductId;
  creationDate: DateString;
  name: string;
  coordinatesX: number;
  coordinatesY: number;
  price: number;
  partNumber: string;
  manufactureCost: number;
  unitOfMeasure?: UnitOfMeasure;
  manufacturerId?: OrganizationId;
  manufacturerName?: string;
  manufacturerFullName?: string;
  manufacturerAnnualTurnover?: number;
  manufacturerEmployeesCount?: number;
};

export const productsToColumns = (products: Product[]): ProductColumns[] =>
  products.map(product => {
    const productColumns: ProductColumns = {
      id: product.id,
      creationDate: product.creationDate,
      name: product.name,
      coordinatesX: product.coordinates.x,
      coordinatesY: product.coordinates.y,
      price: product.price,
      partNumber: product.partNumber,
      manufactureCost: product.manufactureCost,
      unitOfMeasure: product.unitOfMeasure,
    };

    if (product.manufacturer) {
      productColumns.manufacturerId = product.manufacturer.id;
      productColumns.manufacturerName = product.manufacturer.name;
      productColumns.manufacturerFullName = product.manufacturer.fullName;
      productColumns.manufacturerAnnualTurnover =
        product.manufacturer.annualTurnover;
      productColumns.manufacturerEmployeesCount =
        product.manufacturer.employeesCount;
    }

    return productColumns;
  });

export const formatDate = (string: Date) =>
  new Date(string).toLocaleDateString(undefined);

export const getDateOnly = (string: Date) =>
  new Date(string).toLocaleDateString(undefined, {
    month: 'numeric',
    day: 'numeric',
  });
