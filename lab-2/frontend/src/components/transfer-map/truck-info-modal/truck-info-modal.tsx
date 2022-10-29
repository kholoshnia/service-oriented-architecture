import { FC } from 'react';
import './truck-info-modal.scss';

import { Button, Modal, Typography } from 'antd';

import ProductTable from 'components/product-table/product-table';
import { Transfer } from 'models/transfer';

type TruckInfoModalProps = {
  open;
  close: () => void;
  transfer: Transfer;
};

const TruckInfoModal: FC<TruckInfoModalProps> = ({ open, close, transfer }) => (
  <Modal
    className="transfer-info-modal"
    title={`Transfer from "${transfer.sender.name}"`}
    open={open}
    onCancel={close}
    width="90%"
    footer={
      <Button type="primary" onClick={close}>
        Ok
      </Button>
    }
  >
    <Typography.Title level={3}>Products in transfer</Typography.Title>
    <ProductTable
      products={transfer.products ?? []}
      total={transfer.products.length}
    />
  </Modal>
);

export default TruckInfoModal;
