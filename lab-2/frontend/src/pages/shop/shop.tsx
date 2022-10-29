import { FC } from 'react';
import './shop.scss';

import { FilterOutlined, ReloadOutlined } from '@ant-design/icons';
import { Button, InputNumber, Select, Typography } from 'antd';
import { NumberParam, StringParam, useQueryParam } from 'use-query-params';

import ProductTable from 'components/product-table';
import UnitOfMeasureFilter from 'components/unit-of-measure-filter/unit-of-measure-filter';
import usePaginatedQuery from 'hooks/use-paginated-query';
import { Product, UnitOfMeasure } from 'models/product';
import filtersApi from 'services/shop/filters-api';
import getEmptyPage from 'utils/empty-page';

type ShopFilter = 'MANUFACTURER' | 'UNIT_OF_MEASURE';

const shopFilters: { [key in ShopFilter]: string } = {
  UNIT_OF_MEASURE: 'unit of measure',
  MANUFACTURER: 'manufacturer',
};

const shopFilterKeys = Object.keys(shopFilters) as ShopFilter[];

const Shop: FC = () => {
  const [selectedFilter = 'UNIT_OF_MEASURE', setSelectedFilter] = useQueryParam(
    'filter',
    StringParam
  );

  const [unitOfMeasure = UnitOfMeasure.KILOGRAMS, setUnitOfMeasure] =
    useQueryParam('unitOfMeasure', StringParam);
  const [manufacturerId, setManufacturerId] = useQueryParam(
    'manufacturer',
    NumberParam
  );

  const {
    data: products,
    refetch,
    isLoading,
    isError,
    total,
    pagination,
    setPagination,
  } = usePaginatedQuery('shop', pagination => {
    if (selectedFilter === 'MANUFACTURER' && manufacturerId) {
      return filtersApi.getProductsByManufacturer(manufacturerId, pagination);
    }
    if (selectedFilter === 'UNIT_OF_MEASURE') {
      return filtersApi.getProductsByUnitOfMeasure(
        unitOfMeasure as UnitOfMeasure,
        pagination
      );
    }
    return Promise.resolve(getEmptyPage<Product>());
  });

  const reset = () => {
    setSelectedFilter(undefined, 'replace');
  };

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
                value={unitOfMeasure as UnitOfMeasure}
                onChange={value => setUnitOfMeasure(value as any)}
              />
            </>
          )}
        </div>
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <Button
            style={{ marginRight: '1rem', width: '100%' }}
            icon={<ReloadOutlined />}
            onClick={reset}
          >
            Reset
          </Button>
          <Button
            style={{ width: '100%' }}
            type="primary"
            icon={<FilterOutlined />}
            onClick={() => {
              setPagination({
                page: undefined,
                size: undefined,
                sort: undefined,
              });
              refetch();
            }}
          >
            Filter
          </Button>
        </div>
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
        fixName={true}
      />
    </div>
  );
};

export default Shop;
