import { FC } from 'react';
import './storage.scss';

import { PlusOutlined } from '@ant-design/icons';
import { useMutation } from '@tanstack/react-query';
import { Button, Divider, Typography } from 'antd';
import { AxiosError } from 'axios';
import { BooleanParam, useQueryParam } from 'use-query-params';

import NewProductModal from 'components/new-product-modal';
import ProductInfoModal from 'components/product-info-modal';
import ProductTable from 'components/product-table';
import usePaginatedQuery from 'hooks/use-paginated-query';
import useProductFilters from 'hooks/use-product-filters';
import storageApi from 'services/storage-api';
import { getErrorMessage } from 'utils/server-error';

const Storage: FC = () => {
  const { filters, setFilters } = useProductFilters();

  const {
    refetch,
    data: products,
    isLoading,
    isError,
    error,
    total,
    pagination,
    setPagination,
  } = usePaginatedQuery(['storage-products', filters], pagination =>
    storageApi.getProducts(pagination, filters)
  );

  const [productModelOpen, setProductModelOpen] = useQueryParam(
    'addProduct',
    BooleanParam
  );

  const [maxPriceModelOpen, setMaxPriceModelOpen] = useQueryParam(
    'maxPrice',
    BooleanParam
  );

  const {
    mutate: getProductWithMaxPrice,
    data: maxPriceProduct,
    isLoading: maxProductLoading,
  } = useMutation(
    ['max-price'],
    () => storageApi.getProductWithMaxPrice().then(r => r.data),
    {
      onSuccess: () => setMaxPriceModelOpen(true),
    }
  );

  return (
    <div className="storage">
      <Typography.Title level={2}>Products storage</Typography.Title>
      <section className="storage__actions">
        <Typography.Paragraph className="storage__description">
          Here you can view, filter, sort and modify all products.
        </Typography.Paragraph>
        <Button
          icon={<PlusOutlined />}
          type="primary"
          onClick={() => setProductModelOpen(true)}
        >
          Add product
        </Button>
        <NewProductModal
          isOpen={productModelOpen as boolean}
          onSuccess={() => {
            refetch();
            setProductModelOpen(false);
          }}
          onCancel={() => setProductModelOpen(false)}
        />
      </section>
      {isError && (
        <div className="storage__error">
          <span>
            Error receiving products!
            {` ${getErrorMessage(error as AxiosError)}.`}
          </span>
        </div>
      )}
      <ProductTable
        products={products}
        loading={isLoading}
        total={total}
        pagination={pagination}
        onPagination={setPagination}
        filters={filters}
        onFilters={setFilters}
        actions={true}
        refetch={refetch}
      />
      <Divider />
      <Typography.Title level={3}>Additional actions</Typography.Title>
      <section>
        <Button
          loading={maxProductLoading}
          onClick={() => getProductWithMaxPrice()}
        >
          Show product with max price
        </Button>
        <ProductInfoModal
          product={maxPriceProduct}
          isOpen={maxPriceModelOpen as boolean}
          onSuccess={() => setMaxPriceModelOpen(false)}
          title="Product with max price"
        />
      </section>
    </div>
  );
};

export default Storage;
