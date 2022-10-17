export type DateString = string;

export type Page<Data> = {
  page: number;
  size: number;
  total: number;
  data: Data[];
};

export type PaginationParams = {
  page?: number;
  size?: number;
  sort?: string[];
};
