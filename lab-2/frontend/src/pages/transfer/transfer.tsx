import { FC } from 'react';
import './transfer.scss';

import { Pagination, Tag, Typography } from 'antd';

import TransferMap from 'components/transfer-map';
import usePaginatedQuery from 'hooks/use-paginated-query';
import organizationsApi from 'services/storage/organizations-api';
import transferApi from 'services/storage/transfer-api';

const Transfer: FC = () => {
  const {
    data: organizations,
    total: organizationsTotal,
    pagination: organizationsPagination,
    setPagination: setOrganizationsPagination,
  } = usePaginatedQuery('organizations', organizationsApi.getOrganizations, {
    pagination: { size: 50 },
  });

  const {
    data: transfers,
    refetch: refetchTransfers,
    total: transfersTotal,
    pagination: transfersPagination,
    setPagination: setTransfersPagination,
  } = usePaginatedQuery(
    'transfers',
    pagination => transferApi.getTransfers(pagination, false),
    { pagination: { size: 50 } }
    // { refetchInterval: 1000 } // todo enable add filters and description
  );

  return (
    <div className="transfer">
      <div className="transfer__header">
        <div>
          <Typography.Title className="transfer__title" level={2}>
            Transfer map
          </Typography.Title>
          <span>Here you can control the transfer process using </span>
          <Tag style={{ marginRight: 0 }}>w</Tag>
          <Tag style={{ margin: 0 }}>a</Tag>
          <Tag style={{ margin: 0 }}>s</Tag>
          <Tag style={{ margin: 0 }}>d</Tag> or arrow keys.
        </div>

        <div className="transfer__pagination">
          <div>
            <span>Organizations: </span>
            <Pagination
              showSizeChanger={true}
              total={organizationsTotal}
              current={organizationsPagination?.page ?? 1}
              pageSize={organizationsPagination?.size ?? 50}
              pageSizeOptions={[10, 50, 100]}
              defaultPageSize={50}
              size="small"
              onChange={(page, size) =>
                setOrganizationsPagination({
                  ...organizationsPagination,
                  page,
                  size,
                })
              }
            />
          </div>

          <div>
            <span>Transfers: </span>
            <Pagination
              showSizeChanger={true}
              total={transfersTotal}
              current={transfersPagination?.page ?? 1}
              pageSize={transfersPagination?.size ?? 50}
              pageSizeOptions={[10, 50, 100]}
              defaultPageSize={50}
              size="small"
              onChange={(page, size) =>
                setTransfersPagination({
                  ...transfersPagination,
                  page,
                  size,
                })
              }
            />
          </div>
        </div>
      </div>

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
