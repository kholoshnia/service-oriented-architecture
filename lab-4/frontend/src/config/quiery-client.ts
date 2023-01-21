import { QueryClient } from '@tanstack/react-query';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      keepPreviousData: true,
      refetchOnWindowFocus: process.env.NODE_ENV !== 'development',
      retry: process.env.NODE_ENV !== 'development',
    },
  },
});

export default queryClient;
