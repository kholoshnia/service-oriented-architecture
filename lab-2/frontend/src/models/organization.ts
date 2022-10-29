export type Coordinates = {
  x: number;
  y: number;
};

export type OrganizationId = number;

export type Organization = {
  id: OrganizationId;
  name: string;
  fullName?: string;
  annualTurnover?: number;
  employeesCount?: number;
  coordinates: Coordinates;
};

export type NewOrganization = {
  name: string;
  fullName?: string;
  annualTurnover?: number;
  employeesCount?: number;
  coordinates: Coordinates;
};

export type OrganizationFilters = {
  id: OrganizationId;
  name: string;
  fullName?: string;
  annualTurnover?: number;
  employeesCount?: number;
  coordinatesX?: number;
  coordinatesY?: number;
};
