import { FC, useRef } from 'react';

import { FilterFilled } from '@ant-design/icons';
import { Checkbox, Tooltip } from 'antd';
import { ColumnsType, ColumnType } from 'antd/es/table';

import FilterDropdown, {
  CreationDateFilter,
  FloatFilter,
  InputFilter,
  IntegerFilter,
  UnitOfMeasureFilter,
} from 'components/filter-dropdown';
import { FilterProps } from 'components/filter-dropdown/filter-dropdown';
import ProductActions from 'components/product-actions';
import { ProductTableActions } from 'components/product-table/product-table';
import { ProductFilters, ProductId } from 'models/product';
import { PaginationParams } from 'models/utils';
import { formatDate, getDateOnly, ProductColumns } from 'utils/product-helpers';
import { setSortParams } from 'utils/sort-helpers';
import { shortenString } from 'utils/string-helpers';

type UseProductColumns = {
  pagination?: PaginationParams;
  filters?: ProductFilters;
  onFilters?: (filters?: ProductFilters) => void;
  organizationFilter?: boolean;
  actions?: ProductTableActions;
  refetch?: () => void;
  fixName?: boolean;
  onSelect?: (productsId: ProductId, checked: boolean) => void;
  selected?: Set<ProductId>;
  sortable?: boolean;
};

const useProductColumns = ({
  pagination,
  filters,
  onFilters,
  organizationFilter = true,
  actions,
  refetch,
  fixName,
  onSelect,
  selected,
  sortable,
}: UseProductColumns): ColumnsType<ProductColumns> => {
  const searchInput = useRef<any>(null);

  const getFilterProps = (
    dataIndex,
    FilterInput: FC<FilterProps>
  ): ColumnType<ProductColumns> | undefined =>
    filters && {
      filterDropdown: ({ confirm }) => (
        <FilterDropdown
          confirm={confirm}
          filters={filters}
          onFilters={onFilters}
          dataIndex={dataIndex}
          FilterInput={FilterInput}
          inputRef={searchInput}
        />
      ),
      filterIcon: () => (
        <FilterFilled
          style={{ color: filters[dataIndex] ? '#1890ff' : undefined }}
        />
      ),
      onFilterDropdownOpenChange: visible => {
        if (visible) {
          setTimeout(() => searchInput.current?.select(), 100);
        }
      },
    };

  const productColumns: ColumnsType<ProductColumns> = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      sorter: sortable ? { multiple: 1 } : undefined,
      fixed: 'left',
      ...getFilterProps('id', IntegerFilter),
    },
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
      sorter: sortable ? { multiple: 1 } : undefined,
      fixed: fixName ? 'left' : undefined,
      ...getFilterProps('name', InputFilter),
    },
    {
      title: 'Created',
      dataIndex: 'creationDate',
      key: 'creationDate',
      sorter: sortable ? { multiple: 1 } : undefined,
      render: creationDate => (
        <Tooltip title={formatDate(creationDate)}>
          {getDateOnly(creationDate)}
        </Tooltip>
      ),
      ...getFilterProps('creationDate', CreationDateFilter),
    },
    {
      title: 'Price',
      dataIndex: 'price',
      key: 'price',
      sorter: sortable ? { multiple: 1 } : undefined,
      ...getFilterProps('price', IntegerFilter),
    },
    {
      title: 'Part number',
      dataIndex: 'partNumber',
      key: 'partNumber',
      sorter: sortable ? { multiple: 1 } : undefined,
      render: partNumber => (
        <Tooltip title={partNumber}>{shortenString(partNumber, 20)}</Tooltip>
      ),
      ...getFilterProps('partNumber', InputFilter),
    },
    {
      title: 'Cost',
      dataIndex: 'manufactureCost',
      key: 'manufactureCost',
      sorter: sortable ? { multiple: 1 } : undefined,
      ...getFilterProps('manufactureCost', IntegerFilter),
    },
    {
      title: 'Unit of measure',
      dataIndex: 'unitOfMeasure',
      key: 'unitOfMeasure',
      sorter: sortable ? { multiple: 1 } : undefined,
      render: unitOfMeasure => unitOfMeasure.toLowerCase(),
      ...getFilterProps('unitOfMeasure', UnitOfMeasureFilter),
    },
    {
      title: 'Manufacturer',
      children: [
        {
          title: 'ID',
          dataIndex: 'manufacturerId',
          key: 'manufacturer.id',
          sorter: sortable ? { multiple: 1 } : undefined,
          ...(organizationFilter &&
            getFilterProps('manufacturerId', IntegerFilter)),
        },
        {
          title: 'Name',
          dataIndex: 'manufacturerName',
          key: 'manufacturer.name',
          sorter: sortable ? { multiple: 1 } : undefined,
          ...getFilterProps('manufacturerName', InputFilter),
        },
        {
          title: 'Full name',
          dataIndex: 'manufacturerFullName',
          key: 'manufacturer.fullName',
          sorter: sortable ? { multiple: 1 } : undefined,
          ...getFilterProps('manufacturerFullName', InputFilter),
        },
        {
          title: 'Annual turnover',
          dataIndex: 'manufacturerAnnualTurnover',
          key: 'manufacturer.annualTurnover',
          sorter: sortable ? { multiple: 1 } : undefined,
          ...getFilterProps('manufacturerAnnualTurnover', IntegerFilter),
        },
        {
          title: 'Employees count',
          dataIndex: 'manufacturerEmployeesCount',
          key: 'manufacturer.employeesCount',
          sorter: sortable ? { multiple: 1 } : undefined,
          ...getFilterProps('manufacturerEmployeesCount', IntegerFilter),
        },
        {
          title: 'Coordinates',
          children: [
            {
              title: 'X',
              dataIndex: 'manufacturerCoordinatesX',
              key: 'manufacturer.coordinates.x',
              sorter: sortable ? { multiple: 1 } : undefined,
              ...getFilterProps('manufacturerCoordinatesX', FloatFilter),
            },
            {
              title: 'Y',
              dataIndex: 'manufacturerCoordinatesY',
              key: 'manufacturer.coordinates.y',
              sorter: sortable ? { multiple: 1 } : undefined,
              ...getFilterProps('manufacturerCoordinatesY', FloatFilter),
            },
          ],
        },
      ],
    },
  ];

  if (actions === ProductTableActions.EDIT_DELETE) {
    productColumns.push({
      title: 'Actions',
      key: 'actions',
      fixed: 'right',
      render: (_, productColumns) => (
        <ProductActions productColumns={productColumns} refetch={refetch} />
      ),
    });
  } else if (actions === ProductTableActions.SELECT) {
    productColumns.push({
      title: 'Actions',
      key: 'actions',
      fixed: 'right',
      render: (_, productColumns) => (
        <Checkbox
          checked={selected?.has(productColumns.id)}
          onChange={e => onSelect?.(productColumns.id, e.target.checked)}
        />
      ),
    });
  }

  return setSortParams(productColumns, pagination?.sort);
};

export default useProductColumns;
