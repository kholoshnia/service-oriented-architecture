import React, { FC } from 'react';
import './layout.scss';

import { Layout as AntdLayout } from 'antd';
import { Outlet } from 'react-router';
import { QueryParamProvider } from 'use-query-params';
import { ReactRouter6Adapter } from 'use-query-params/adapters/react-router-6';

import Header from 'components/layout/header';

const Layout: FC = () => (
  <AntdLayout className="layout">
    <AntdLayout.Header>
      <Header />
    </AntdLayout.Header>
    <AntdLayout.Content className="layout__content">
      <QueryParamProvider adapter={ReactRouter6Adapter}>
        <Outlet />
      </QueryParamProvider>
    </AntdLayout.Content>
    <AntdLayout.Footer className="layout__footer">
      SOA lab 2 © Vadim Kholoshnia P34111
    </AntdLayout.Footer>
  </AntdLayout>
);

export default Layout;
