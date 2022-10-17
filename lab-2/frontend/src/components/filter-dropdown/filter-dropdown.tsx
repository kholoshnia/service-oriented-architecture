import { FC, useState } from 'react';

import { SearchOutlined } from '@ant-design/icons';
import { Button, Space } from 'antd';

import { ProductColumns } from 'utils/product-helpers';

export type FilterProps = {
  inputRef: any;
  value?: any;
  onChange: (value) => void;
  confirm: () => void;
};

type FilterDropdownProps = {
  confirm: () => void;
  filters: ProductColumns;
  onFilters?: (filters?: ProductColumns) => void;
  dataIndex: string;
  FilterInput: FC<FilterProps>;
  inputRef: any;
};

const FilterDropdown: FC<FilterDropdownProps> = ({
  confirm,
  filters,
  onFilters,
  dataIndex,
  FilterInput,
  inputRef,
}) => {
  const [value, setValue] = useState(filters[dataIndex]);

  const onConfirm = () => {
    onFilters?.({ ...filters, [dataIndex]: value });
    confirm();
  };

  const onReset = () => {
    setValue(undefined);
    onFilters?.({ ...filters, [dataIndex]: undefined });
    confirm();
  };

  return (
    <div style={{ padding: 8 }}>
      <FilterInput
        inputRef={inputRef}
        value={value}
        onChange={value => setValue(value ? [value] : [])}
        confirm={onConfirm}
      />
      <Space>
        <Button
          type="primary"
          onClick={onConfirm}
          icon={<SearchOutlined />}
          size="small"
          style={{ width: 90, marginTop: 8 }}
        >
          Search
        </Button>
        <Button
          onClick={onReset}
          size="small"
          style={{ width: 90, marginTop: 8 }}
        >
          Reset
        </Button>
      </Space>
    </div>
  );
};

export default FilterDropdown;
