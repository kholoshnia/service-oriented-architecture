import { FC, useState } from 'react';

import { DeleteOutlined, EditOutlined } from '@ant-design/icons';
import { useMutation } from '@tanstack/react-query';
import { Button, notification, Popconfirm } from 'antd';
import { AxiosError } from 'axios';

import NewProductModal from 'components/new-product-modal';
import storageApi from 'services/storage-api';
import { ProductColumns } from 'utils/product-helpers';
import { showError } from 'utils/server-error';

type ProductActionsProps = {
  productColumns: ProductColumns;
  refetch?: () => void;
};

const ProductActions: FC<ProductActionsProps> = ({
  productColumns,
  refetch,
}) => {
  const [editModal, setEditModal] = useState(false);

  const { mutate: deleteProduct } = useMutation(
    ['delete-product'],
    storageApi.deleteProduct,
    {
      onError: (e: AxiosError) => showError(e, 'Could not delete product!'),
      onSuccess: () => {
        notification['success']({
          message: 'Product deleted!',
        });
        refetch?.();
      },
    }
  );

  return (
    <div
      style={{
        display: 'flex',
        justifyContent: 'center',
      }}
    >
      <Button
        style={{ marginRight: '0.5rem' }}
        icon={<EditOutlined />}
        type="primary"
        onClick={() => setEditModal(true)}
      />
      <NewProductModal
        isOpen={editModal}
        onSuccess={() => {
          refetch?.();
          setEditModal(false);
        }}
        onCancel={() => setEditModal(false)}
        update={true}
        productColumns={productColumns}
      />

      <Popconfirm
        title="Are you sure to delete this product?"
        onConfirm={() => deleteProduct(productColumns.id)}
        okText="Yes"
        cancelText="No"
        placement="left"
      >
        <Button type="primary" icon={<DeleteOutlined />} />
      </Popconfirm>
    </div>
  );
};

export default ProductActions;
