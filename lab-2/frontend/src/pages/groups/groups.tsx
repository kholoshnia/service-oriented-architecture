import { FC } from 'react';
import './groups.scss';

import { Typography } from 'antd';
import { AxiosError } from 'axios';

import GroupsTable from 'components/groups-table';
import usePaginatedQuery from 'hooks/use-paginated-query';
import productsApi from 'services/storage/products-api';
import { getErrorMessage } from 'utils/server-error';

const Groups: FC = () => {
  const {
    data: groups,
    isLoading,
    isError,
    error,
    total,
    pagination,
    setPagination,
  } = usePaginatedQuery('groups', pagination =>
    productsApi.getManufactureCostGroups(pagination)
  );

  return (
    <div className="groups">
      <Typography.Title level={2}>Manufacture cost groups</Typography.Title>
      {isError && (
        <div className="groups__error">
          <span>
            Error receiving groups!
            {` ${getErrorMessage(error as AxiosError)}.`}
          </span>
        </div>
      )}
      <GroupsTable
        groups={groups}
        loading={isLoading}
        total={total}
        pagination={pagination}
        onPagination={setPagination}
      />
    </div>
  );
};

export default Groups;
