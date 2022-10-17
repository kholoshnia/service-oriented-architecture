import { NewOrganization, Organization } from 'models/organization';
import { DateString } from 'models/utils';

export enum UnitOfMeasure {
  KILOGRAMS = 'KILOGRAMS',
  CENTIMETERS = 'CENTIMETERS',
  PCS = 'PCS',
  MILLILITERS = 'MILLILITERS',
  MILLIGRAMS = 'MILLIGRAMS',
}

export type Coordinates = {
  x: number;
  y: number;
};

export type ProductId = number;

export type Product = {
  id: ProductId;
  creationDate: DateString;
  name: string;
  coordinates: Coordinates;
  price: number;
  partNumber: string;
  manufactureCost: number;
  unitOfMeasure?: UnitOfMeasure;
  manufacturer?: Organization;
};

export type NewProduct = {
  name: string;
  coordinates: Coordinates;
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
