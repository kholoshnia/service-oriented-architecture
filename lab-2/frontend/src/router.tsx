import { createBrowserRouter } from 'react-router-dom';
import Shop from 'pages/shop';
import Storage from 'pages/storage';

const router = createBrowserRouter([
  {
    path: '/',
    element: <div>Hello world!</div>,
  },
  {
    path: '/shop',
    element: <Shop/>
  },
  {
    path: '/storage',
    element: <Storage/>
  }
]);

export default router;
