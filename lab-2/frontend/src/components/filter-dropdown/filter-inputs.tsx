import { FC } from 'react';

import { Calendar, Input, InputNumber, Select } from 'antd';

import { unitsOfMeasure } from 'components/unit-of-measure-filter/unit-of-measure-filter';

import { FilterProps } from './filter-dropdown';

export const InputFilter: FC<FilterProps> = ({
  inputRef,
  value,
  onChange,
  confirm,
}) => (
  <Input
    ref={inputRef}
    placeholder="Search text"
    value={value}
    onChange={e => onChange(e.target.value)}
    onPressEnter={confirm}
    style={{ display: 'block' }}
  />
);

export const IntegerFilter: FC<FilterProps> = ({
  inputRef,
  value,
  onChange,
  confirm,
}) => (
  <InputNumber
    ref={inputRef}
    placeholder="Search integer"
    value={value}
    onChange={onChange}
    onPressEnter={confirm}
    style={{ display: 'block', width: 'auto' }}
    parser={value => Math.trunc(Number(value))}
  />
);

export const FloatFilter: FC<FilterProps> = ({
  inputRef,
  value,
  onChange,
  confirm,
}) => (
  <InputNumber
    ref={inputRef}
    placeholder="Search float"
    value={value}
    onChange={onChange}
    onPressEnter={confirm}
    style={{ display: 'block', width: 'auto' }}
  />
);

export const CreationDateFilter: FC<FilterProps> = ({ onChange }) => (
  <Calendar
    style={{ width: '300px' }}
    fullscreen={false}
    onChange={value => onChange(value.format('YYYY-MM-DD'))}
  />
);

export const UnitOfMeasureFilter: FC<FilterProps> = ({
  inputRef,
  value,
  onChange,
}) => (
  <Select
    ref={inputRef}
    placeholder="Select unit of measure"
    value={value}
    onChange={onChange}
    style={{ display: 'block', width: 'auto' }}
  >
    {unitsOfMeasure.map(unitOfMeasure => (
      <Select.Option key={unitOfMeasure} value={unitOfMeasure}>
        {unitOfMeasure.toLowerCase()}
      </Select.Option>
    ))}
  </Select>
);
