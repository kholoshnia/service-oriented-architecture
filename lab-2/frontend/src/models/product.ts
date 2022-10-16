import { NewOrganization, Organization } from 'models/organization';

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
  creationDate: Date;
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

export type ManufactureCostGroupDto = {
  manufactureCost: number;
  count: number;
};
