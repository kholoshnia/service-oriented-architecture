import { NewOrganization, Organization } from 'models/organization';
import { DateString } from 'models/utils';
import { ProductColumns } from 'utils/product-helpers';

export enum UnitOfMeasure {
  KILOGRAMS = 'KILOGRAMS',
  CENTIMETERS = 'CENTIMETERS',
  PCS = 'PCS',
  MILLILITERS = 'MILLILITERS',
  MILLIGRAMS = 'MILLIGRAMS',
}

export type ProductId = number;

export type Product = {
  id: ProductId;
  creationDate: DateString;
  name: string;
  price: number;
  partNumber: string;
  manufactureCost: number;
  unitOfMeasure?: UnitOfMeasure;
  manufacturer?: Organization;
};

export type NewProduct = {
  name: string;
  price: number;
  partNumber: string;
  manufactureCost: number;
  unitOfMeasure?: UnitOfMeasure;
  manufacturer?: NewOrganization;
};

export type ManufactureCostGroup = {
  manufactureCost: number;
  count: number;
};

export type ProductFilters = Partial<ProductColumns> & {
  transferred?: boolean;
};
