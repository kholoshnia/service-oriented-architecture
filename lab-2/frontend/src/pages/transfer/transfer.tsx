import { FC } from 'react';

import { Typography } from 'antd';

import TransferMap from 'components/transfer-map';
import usePaginatedQuery from 'hooks/use-paginated-query';
import organizationsApi from 'services/storage/organizations-api';
import transferApi from 'services/storage/transfer-api';

const Transfer: FC = () => {
  const { data: organizations } = usePaginatedQuery(
    'organizations',
    organizationsApi.getOrganizations
  );

  const { data: transfers, refetch: refetchTransfers } = usePaginatedQuery(
    'transfers',
    transferApi.getTransfers,
    { refetchInterval: 5000 }
  );

  return (
    <div className="transfer">
      <Typography.Title level={2}>Transfer map</Typography.Title>
      {organizations && transfers && (
        <TransferMap
          organizations={organizations}
          transfers={transfers}
          refetchTransfers={refetchTransfers}
        />
      )}
    </div>
  );
};

export default Transfer;
