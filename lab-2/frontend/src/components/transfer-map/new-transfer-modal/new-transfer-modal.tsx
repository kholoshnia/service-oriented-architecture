import { FC, useEffect, useState } from 'react';
import './new-transfer-modal.scss';

import { useMutation } from '@tanstack/react-query';
import { Button, Modal, notification } from 'antd';
import { AxiosError } from 'axios';

import ProductTable from 'components/product-table';
import { ProductTableActions } from 'components/product-table/product-table';
import TruckWithBoxes from 'components/transfer-map/new-transfer-modal/truck-with-boxes/truck-with-boxes';
import usePaginatedQuery from 'hooks/use-paginated-query';
import useProductFilters from 'hooks/use-product-filters';
import { Organization } from 'models/organization';
import { ProductId } from 'models/product';
import { Transfer } from 'models/transfer';
import productsApi from 'services/storage/products-api';
import transferApi from 'services/storage/transfer-api';
import { Normalized } from 'utils/map-helpers';
import { showError } from 'utils/server-error';

type NewTransferModalProps = {
  open;
  close: () => void;
  organization: Normalized<Organization>;
  refetchTransfers: () => void;
  setControlledTruck: (transfer: Normalized<Transfer>) => void;
};

const NewTransferModal: FC<NewTransferModalProps> = ({
  open,
  close,
  organization,
  refetchTransfers,
  setControlledTruck,
}) => {
  const [gone, setGone] = useState(false);
  const [productIds, setProductIds] = useState<Set<ProductId>>(new Set());

  const { mutate: addTransfer } = useMutation(
    ['add-transfer'],
    transferApi.addTransfer,
    {
      onSuccess: r => {
        refetchTransfers();
        setControlledTruck({
          ...r.data,
          normalized: organization.normalized,
        });
        setGone(true);
      },
      onError: (e: AxiosError) => showError(e, 'Could not start transfer!'),
    }
  );

  const { filters, setFilters } = useProductFilters();
  const { data, refetch, isLoading, total, pagination, setPagination } =
    usePaginatedQuery(
      'storage',
      pagination =>
        productsApi.getTransferProductsPage(
          organization.id,
          pagination,
          filters
        ),
      { deps: [filters] }
    );

  const onSelect = (productId: ProductId, selected: boolean) =>
    setProductIds(prev => {
      const copy = new Set(prev);
      if (selected) copy.add(productId);
      else copy.delete(productId);
      return copy;
    });

  const onStart = () => {
    addTransfer({
      products: Array.from(productIds),
      senderId: organization.id,
    });
  };

  useEffect(() => {
    let timeout: any;
    if (gone) {
      timeout = setTimeout(() => {
        close();
        setGone(false);
        notification['success']({
          message: 'Transfer started!',
        });
      }, 1000);
    }
    return () => timeout && clearTimeout(timeout);
  }, [gone]);

  return (
    <Modal
      className="new-transfer-modal"
      title={`New transfer from "${organization.name}"`}
      open={open}
      onCancel={close}
      width="90%"
      footer={
        <>
          <Button onClick={close}>Cancel</Button>
          <Button
            type="primary"
            onClick={onStart}
            disabled={productIds.size === 0}
          >
            Go!
          </Button>
        </>
      }
    >
      <div className="new-transfer-modal__content">
        <div className="new-transfer-modal__table">
          <ProductTable
            products={data}
            loading={isLoading}
            total={total}
            pagination={pagination}
            onPagination={setPagination}
            filters={filters}
            onFilters={setFilters}
            organizationFilter={false}
            refetch={refetch}
            actions={ProductTableActions.SELECT}
            onSelect={onSelect}
            selected={productIds}
          />
        </div>
        <div className="new-transfer-modal__truck">
          <TruckWithBoxes gone={gone} boxesCount={productIds.size} />
        </div>
      </div>
    </Modal>
  );
};

export default NewTransferModal;
