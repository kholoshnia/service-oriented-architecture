import { Coordinates, Organization, OrganizationId } from 'models/organization';
import { Product, ProductId } from 'models/product';
import { DateTimeString } from 'models/utils';

export type TransferId = number;

export type NewTransfer = {
  products: ProductId[];
  senderId: OrganizationId;
};

export type Transfer = {
  id: ProductId;
  coordinates: Coordinates;
  products: Product[];
  sender: Organization;
  receiver?: Organization;
  startedAt: DateTimeString;
  finishedAt: DateTimeString;
};
