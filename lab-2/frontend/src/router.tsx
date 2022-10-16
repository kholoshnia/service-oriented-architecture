import { createBrowserRouter, Navigate } from 'react-router-dom';

import Layout from 'components/layout';
import Delivery from 'pages/delivery';
import Shop from 'pages/shop';
import Storage from 'pages/storage';

const router = createBrowserRouter([
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
        path: '/delivery',
        element: <Delivery />,
      },
    ],
  },
]);

export default router;