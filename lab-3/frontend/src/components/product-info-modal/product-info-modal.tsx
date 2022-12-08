import { FC } from 'react';
import './product-info-modal.scss';

import { Modal } from 'antd';

import { Product } from 'models/product';

type ProductInfoModalProps = {
  product?: Product;
  isOpen: boolean;
  onSuccess: () => void;
  title?: string;
};

const ProductInfoModal: FC<ProductInfoModalProps> = ({
  product,
  isOpen,
  onSuccess,
  title = 'Product info',
}) => {
  if (!product) {
    return null;
  }

  return (
    <Modal
      title={title}
      open={isOpen}
      onOk={onSuccess}
      cancelButtonProps={{
        style: { display: 'none' },
      }}
      className="product-info-modal"
    >
      <section className="product-info-modal__content">
        <div>
          <strong>ID:</strong>
          <span>{product.id}</span>
        </div>
        <div>
          <strong>Name:</strong>
          <span>{product.name}</span>
        </div>
        <div>
          <strong>Price:</strong>
          <span>{product.price}</span>
        </div>
        <div>
          <strong>Part number:</strong>
          <span>{product.partNumber}</span>
        </div>
        <div>
          <strong>Manufacture cost:</strong>
          <span>{product.partNumber}</span>
        </div>
        <div>
          <strong>Unit of measure:</strong>
          <span>{product.unitOfMeasure?.toLowerCase()}</span>
        </div>

        {product.manufacturer ? (
          <>
            <div
              style={{
                display: 'flex',
                justifyContent: 'center',
                marginTop: '1rem',
                marginBottom: '1rem',
              }}
            >
              Manufacturer
            </div>
            <div>
              <strong>ID:</strong> {product.manufacturer.id}
            </div>
            <div>
              <strong>Name:</strong> {product.manufacturer.name}
            </div>
            <div>
              <strong>Full name:</strong> {product.manufacturer.fullName}
            </div>
            <div>
              <strong>Annual turnover:</strong>{' '}
              {product.manufacturer.annualTurnover}
            </div>
            <div>
              <strong>Employees count:</strong>{' '}
              {product.manufacturer.employeesCount}
            </div>
            <div>
              <strong>X coordinate:</strong>
              <span>{product.manufacturer.coordinates.x}</span>
            </div>
            <div>
              <strong>Y coordinate:</strong>
              <span>{product.manufacturer.coordinates.y}</span>
            </div>
          </>
        ) : (
          <div
            style={{
              display: 'flex',
              justifyContent: 'center',
              marginTop: '1rem',
            }}
          >
            Product does not have manufacturer
          </div>
        )}
      </section>
    </Modal>
  );
};

export default ProductInfoModal;
