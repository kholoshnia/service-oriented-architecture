export type OrganizationId = number;

export type Organization = {
  id: OrganizationId;
  name: string;
  fullName?: string;
  annualTurnover?: number;
  employeesCount?: number;
};

export type NewOrganization = {
  name: string;
  fullName?: string;
  annualTurnover?: number;
  employeesCount?: number;
};
