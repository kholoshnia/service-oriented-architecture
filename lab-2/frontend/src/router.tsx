import { createBrowserRouter, Navigate } from 'react-router-dom';

import Layout from 'components/layout';
import Groups from 'pages/groups';
import PartNumber from 'pages/part-number';
import Shop from 'pages/shop';
import Storage from 'pages/storage';
import Transfer from 'pages/transfer';

const router = createBrowserRouter(
  [
    {
      path: '/',
      element: <Navigate to="/shop" />,
    },
    {
      element: <Layout />,
      children: [
        {
          path: '/shop',
          element: <Shop />,
        },
        {
          path: '/storage',
          element: <Storage />,
        },
        {
          path: '/groups',
          element: <Groups />,
        },
        {
          path: '/part-number',
          element: <PartNumber />,
        },
        {
          path: '/transfer',
          element: <Transfer />,
        },
      ],
    },
  ],
  { basename: process.env.PUBLIC_URL }
);

export default router;
