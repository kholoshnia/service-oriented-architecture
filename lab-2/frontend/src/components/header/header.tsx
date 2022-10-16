import React, { FC } from 'react';
import './header.scss';

import { ShopOutlined, DatabaseOutlined, CarOutlined } from '@ant-design/icons';
import { Menu, MenuProps, Typography } from 'antd';
import { useLocation, useNavigate } from 'react-router';

import logoSrc from 'assets/images/logo.png';

const { Title } = Typography;

const pathToKey: {
  [path: string]: number;
} = {
  '/shop': 1,
  '/storage': 2,
  '/delivery': 3,
};

const Header: FC = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const menuItems: MenuProps['items'] = [
    {
      key: 1,
      label: 'Shop',
      icon: <ShopOutlined />,
      onClick: () => navigate('/shop'),
    },
    {
      key: 2,
      label: 'Storage',
      icon: <DatabaseOutlined />,
      onClick: () => navigate('/storage'),
    },
    {
      key: 3,
      label: 'Delivery',
      icon: <CarOutlined />,
      onClick: () => navigate('/delivery'),
    },
  ];

  const getSelectedKey = () => pathToKey[location.pathname];

  return (
    <>
      <div className="header-logo">
        <img src={logoSrc} alt="logo" height="35px" />
        <Title className="header-logo__title" level={3}>
          Online shop
        </Title>
      </div>
      <div className="header-menu">
        <Menu
          theme="dark"
          mode="horizontal"
          selectedKeys={[`${getSelectedKey()}`]}
          items={menuItems}
        />
      </div>
    </>
  );
};

export default Header;
