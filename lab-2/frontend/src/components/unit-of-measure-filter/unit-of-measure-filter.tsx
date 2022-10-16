import { Dispatch, FC, SetStateAction } from 'react';
import './unit-of-measure-filter.scss';

import { Select } from 'antd';
import classNames from 'classnames';

import { UnitOfMeasure } from 'models/product';

type UnitOfMeasureFilterProps = {
  value: UnitOfMeasure;
  onChange: Dispatch<SetStateAction<UnitOfMeasure>>;
  className?: string;
};

const unitsOfMeasure = Object.values(UnitOfMeasure);

const UnitOfMeasureFilter: FC<UnitOfMeasureFilterProps> = ({
  value,
  onChange,
  className,
}) => (
  <Select
    className={classNames('unit-of-measure-filter', className)}
    value={value}
    onChange={onChange}
  >
    {unitsOfMeasure.map(unitOfMeasure => (
      <Select.Option key={unitOfMeasure} value={unitOfMeasure}>
        {unitOfMeasure}
      </Select.Option>
    ))}
  </Select>
);

export default UnitOfMeasureFilter;
