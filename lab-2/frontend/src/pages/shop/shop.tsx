import { FC, useState } from 'react';
import './shop.scss';

import { FilterOutlined } from '@ant-design/icons';
import { Button, InputNumber, Select, Typography } from 'antd';
import { StringParam, useQueryParam } from 'use-query-params';

import ProductTable from 'components/product-table';
import UnitOfMeasureFilter from 'components/unit-of-measure-filter/unit-of-measure-filter';
import usePaginatedQuery from 'hooks/use-paginated-query';
import { Product, UnitOfMeasure } from 'models/product';
import shopApi from 'services/shop-api';
import getEmptyPage from 'utils/empty-page';

type ShopFilter = 'MANUFACTURER' | 'UNIT_OF_MEASURE';

const shopFilters: { [key in ShopFilter]: string } = {
  MANUFACTURER: 'manufacturer',
  UNIT_OF_MEASURE: 'unit of measure',
};

const shopFilterKeys = Object.keys(shopFilters) as ShopFilter[];

const Shop: FC = () => {
  const [selectedFilter, setSelectedFilter] = useQueryParam(
    'filter',
    StringParam
  );

  const [unitOfMeasure, setUnitOfMeasure] = useState(UnitOfMeasure.KILOGRAMS);
  const [manufacturerId, setManufacturerId] = useState<number | null>();

  const {
    data: products,
    refetch,
    isLoading,
    isError,
    pagination,
    setPagination,
    total,
  } = usePaginatedQuery(['shop-products', selectedFilter], pagination => {
    if (selectedFilter === 'MANUFACTURER' && manufacturerId) {
      return shopApi.getProductsByManufacturer(manufacturerId, pagination);
    }
    if (selectedFilter === 'UNIT_OF_MEASURE') {
      return shopApi.getProductsByUnitOfMeasure(unitOfMeasure, pagination);
    }
    return Promise.resolve(getEmptyPage<Product>());
  });

  return (
    <div className="shop">
      <Typography.Title level={2}>Shop filters</Typography.Title>
      <section className="shop__filter-by">
        <div>
          <span>Filter by</span>
          <Select
            className="shop__select"
            value={selectedFilter}
            onChange={s => setSelectedFilter(s as any)}
            defaultValue="MANUFACTURER"
          >
            {shopFilterKeys.map((key, index) => (
              <Select.Option key={index} value={key}>
                {shopFilters[key]}
              </Select.Option>
            ))}
          </Select>
        </div>
        <div>
          {selectedFilter === 'MANUFACTURER' ? (
            <>
              <span>Input manufacturer ID</span>
              <InputNumber
                min={0}
                className="shop__manufacturer-input"
                value={manufacturerId}
                onChange={setManufacturerId}
                placeholder="Manufacturer ID"
                parser={value => Math.trunc(Number(value))}
              />
            </>
          ) : (
            <>
              <span>Select unit of measure</span>
              <UnitOfMeasureFilter
                className="shop__unit-of-measure-select"
                value={unitOfMeasure}
                onChange={setUnitOfMeasure}
              />
            </>
          )}
        </div>
        <Button
          type="primary"
          icon={<FilterOutlined />}
          onClick={() => refetch()}
        >
          Filter
        </Button>
      </section>
      {isError && (
        <div className="shop__error">
          <span>
            Error receiving products! Please check the provided filters.
          </span>
        </div>
      )}
      <ProductTable
        products={products}
        loading={isLoading}
        total={total}
        pagination={pagination}
        onPagination={setPagination}
      />
    </div>
  );
};

export default Shop;
