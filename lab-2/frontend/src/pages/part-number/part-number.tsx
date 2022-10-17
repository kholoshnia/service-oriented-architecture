import { FC } from 'react';
import 'pages/part-number/part-number.scss';

import { Input, Typography } from 'antd';
import { StringParam, useQueryParam } from 'use-query-params';

import ProductTable from 'components/product-table';
import usePaginatedQuery from 'hooks/use-paginated-query';
import storageApi from 'services/storage-api';

const PartNumber: FC = () => {
  const [partNumber = '', setPartNumber] = useQueryParam(
    'partNumber',
    StringParam
  );

  const {
    data: products,
    isLoading,
    isError,
    total,
    pagination,
    setPagination,
  } = usePaginatedQuery(['part-number', partNumber], pagination =>
    storageApi.getProductsWithGreaterPartNumber(
      partNumber as string,
      pagination
    )
  );

  return (
    <div className="part-number">
      <Typography.Title level={2}>Part number filter</Typography.Title>
      <section className="part-number__filter">
        <span>Find products with part number greater than:</span>
        <Input
          className="part-number__input"
          value={partNumber as string}
          onChange={e => setPartNumber(e.target.value as any)}
          placeholder="Part number"
        />
      </section>
      {isError && (
        <div className="part-number__error">
          <span>
            Error receiving products! Please check the provided filter.
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

export default PartNumber;
