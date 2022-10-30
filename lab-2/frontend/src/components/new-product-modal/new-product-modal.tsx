import { FC, useState } from 'react';

import { useMutation } from '@tanstack/react-query';
import {
  Form,
  Input,
  InputNumber,
  Modal,
  notification,
  Select,
  Switch,
} from 'antd';
import { AxiosError } from 'axios';

import { unitsOfMeasure } from 'components/unit-of-measure-filter/unit-of-measure-filter';
import { NewProduct } from 'models/product';
import productsApi from 'services/storage/products-api';
import { clearValues } from 'utils/form-helpers';
import { ProductColumns } from 'utils/product-helpers';
import { showError } from 'utils/server-error';

type NewProductModalProps = {
  isOpen: boolean;
  onSuccess: () => void;
  onCancel: () => void;
  update?: boolean;
  productColumns?: ProductColumns;
};

const NewProductModal: FC<NewProductModalProps> = ({
  isOpen,
  onSuccess,
  onCancel,
  update,
  productColumns,
}) => {
  const [form] = Form.useForm();
  const [includeManufacturer, setIncludeManufacturer] = useState<boolean>(
    !!productColumns?.manufacturerId
  );

  const { mutate: updateProduct, isLoading } = useMutation(
    ['new-product', update],
    (newProduct: NewProduct) =>
      update
        ? productsApi.updateProduct(productColumns!.id, newProduct)
        : productsApi.addProduct(newProduct),
    {
      onError: (e: AxiosError) =>
        showError(e, update ? 'Cannot update product!' : 'Cannot add product!'),
      onSuccess: r => {
        notification['success'](
          update
            ? {
                message: 'Product updated!',
                description: `Updated ID: ${r.data.id}.`,
              }
            : {
                message: 'Product added!',
                description: `Generated ID: ${r.data.id}.`,
              }
        );
        form.resetFields();
        if (!update) setIncludeManufacturer(false);
        onSuccess();
      },
    }
  );

  const onFinish = (values: ProductColumns) => {
    clearValues(values);
    updateProduct({
      name: values.name,
      price: values.price,
      partNumber: values.partNumber,
      manufactureCost: values.manufactureCost,
      unitOfMeasure: values.unitOfMeasure,
      manufacturer: includeManufacturer
        ? {
            name: values.manufacturerName!,
            fullName: values.manufacturerFullName,
            annualTurnover: values.manufacturerAnnualTurnover,
            employeesCount: values.manufacturerEmployeesCount,
            coordinates: {
              x: values.manufacturerCoordinatesX ?? 0,
              y: values.manufacturerCoordinatesY ?? 0,
            },
          }
        : undefined,
    });
  };

  const onUnitOfMeasureChange = (value: string) => {
    form.setFieldsValue({ unitOfMeasure: value });
  };

  return (
    <Modal
      title={update ? 'Update product' : 'Add product'}
      open={isOpen}
      onOk={() => form.submit()}
      onCancel={() => {
        form.resetFields();
        if (!update) setIncludeManufacturer(false);
        onCancel();
      }}
      okButtonProps={{
        loading: isLoading,
      }}
    >
      <Form
        form={form}
        name="basic"
        onFinish={onFinish}
        autoComplete="off"
        labelCol={{ span: 8 }}
        wrapperCol={{ span: 14 }}
        initialValues={productColumns}
      >
        <Form.Item
          label="Name"
          name="name"
          rules={[
            { required: true, message: 'Name  is required!' },
            { whitespace: false, message: 'Name cannot be empty!' },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          label="Price"
          name="price"
          rules={[{ required: true, message: 'Price is required!' }]}
        >
          <InputNumber
            style={{ width: '100%' }}
            min={1}
            parser={value => Math.trunc(Number(value))}
          />
        </Form.Item>
        <Form.Item
          label="Part number"
          name="partNumber"
          rules={[
            {
              min: 25,
              max: 48,
              message: 'Part number length must be > 35 and <= 25!',
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          label="Manufacture cost"
          name="manufactureCost"
          rules={[{ required: true, message: 'Manufacture cost is required!' }]}
        >
          <InputNumber style={{ width: '100%' }} />
        </Form.Item>
        <Form.Item label="Unit of measure" name="unitOfMeasure">
          <Select onChange={onUnitOfMeasureChange}>
            {unitsOfMeasure.map(unitOfMeasure => (
              <Select.Option key={unitOfMeasure} value={unitOfMeasure}>
                {unitOfMeasure.toLowerCase()}
              </Select.Option>
            ))}
          </Select>
        </Form.Item>

        <div
          style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            marginBottom: '1.5rem',
          }}
        >
          <span>Include manufacturer</span>
          <Switch
            checked={includeManufacturer}
            onChange={setIncludeManufacturer}
            style={{ marginLeft: '1rem' }}
          />
        </div>

        {includeManufacturer && (
          <>
            <Form.Item
              label="Name"
              name="manufacturerName"
              rules={[
                { required: true, message: 'Name  is required!' },
                { whitespace: false, message: 'Name cannot be empty!' },
              ]}
            >
              <Input />
            </Form.Item>

            <Form.Item
              label="Full name"
              name="manufacturerFullName"
              rules={[{ max: 1317, message: 'Full name cannot be > 1317!' }]}
            >
              <Input />
            </Form.Item>

            <Form.Item
              label="Annual turnover"
              name="manufacturerAnnualTurnover"
            >
              <InputNumber
                style={{ width: '100%' }}
                min={1}
                parser={value => Math.trunc(Number(value))}
              />
            </Form.Item>

            <Form.Item
              label="Employees count"
              name="manufacturerEmployeesCount"
            >
              <InputNumber
                style={{ width: '100%' }}
                min={1}
                parser={value => Math.trunc(Number(value))}
              />
            </Form.Item>

            <Form.Item
              label="X coordinate"
              name="manufacturerCoordinatesX"
              rules={[{ required: true, message: 'X coordinate is required!' }]}
            >
              <InputNumber style={{ width: '100%' }} max={492} />
            </Form.Item>

            <Form.Item
              label="Y coordinate"
              name="manufacturerCoordinatesY"
              rules={[{ required: true, message: 'X coordinate is required!' }]}
            >
              <InputNumber style={{ width: '100%' }} min={-139} />
            </Form.Item>
          </>
        )}
      </Form>
    </Modal>
  );
};

export default NewProductModal;
