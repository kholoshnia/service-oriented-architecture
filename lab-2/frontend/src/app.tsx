import React, { FC } from 'react';

import { QueryClientProvider } from '@tanstack/react-query';
import { Spin } from 'antd';
import { RouterProvider } from 'react-router';

import queryClient from 'config/quiery-client';
import router from 'router';

const App: FC = () => (
  <QueryClientProvider client={queryClient}>
    <RouterProvider router={router} fallbackElement={<Spin />} />
  </QueryClientProvider>
);

export default App;
