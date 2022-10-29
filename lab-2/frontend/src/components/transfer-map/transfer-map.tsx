import { FC, useEffect, useRef, useState } from 'react';
import './transfer-map.scss';

import { useMutation } from '@tanstack/react-query';
import { notification } from 'antd';
import { AxiosError } from 'axios';
import { NumberParam, useQueryParam } from 'use-query-params';

import OrganizationsMap from 'components/transfer-map/organizations-map';
import TruckMap from 'components/transfer-map/truck-map';
import { Organization, OrganizationId } from 'models/organization';
import { Transfer } from 'models/transfer';
import transferApi from 'services/storage/transfer-api';
import normalizeCoordinates, { Normalized } from 'utils/map-helpers';
import withRandomIcon, { WithIcon } from 'utils/random-icon';
import { showError } from 'utils/server-error';

export type TransferMapProps = {
  organizations: Organization[];
  transfers: Transfer[];
  refetchTransfers: () => void;
};

const TransferMap: FC<TransferMapProps> = ({
  organizations,
  transfers,
  refetchTransfers,
}) => {
  const mapRef = useRef<HTMLDivElement>(null);

  const [preparedOrganizations, setPreparedOrganizations] = useState<
    Normalized<WithIcon<Organization>>[]
  >([]);

  useEffect(() => {
    const normalizeData = () =>
      setPreparedOrganizations(
        normalizeCoordinates(
          withRandomIcon(organizations),
          mapRef.current?.clientWidth,
          mapRef.current?.clientHeight
        )
      );

    normalizeData();
    window.addEventListener('resize', normalizeData);
    return () => window.removeEventListener('resize', normalizeData);
  }, [organizations]);

  const [finishId, setFinishId] = useState<OrganizationId>();

  const [preparedTransfers, setPreparedTransfers] = useState<
    Normalized<Transfer>[]
  >([]);

  useEffect(() => {
    console.log('here');

    const normalizeData = () =>
      setPreparedTransfers(
        normalizeCoordinates(
          transfers,
          mapRef.current?.clientWidth,
          mapRef.current?.clientHeight
        )
      );

    normalizeData();
    window.addEventListener('resize', normalizeData);
    return () => window.removeEventListener('resize', normalizeData);
  }, [transfers]);

  const [controlledTruckId, setControlledTruckId] = useQueryParam(
    'controlledTruck',
    NumberParam
  );

  const { mutate: finishTransfer } = useMutation(
    ['finish-transfer'],
    (organizationId: OrganizationId) =>
      transferApi.finishTransfer(controlledTruckId as number, organizationId),
    {
      onError: (e: AxiosError) => showError(e, 'Could not finish transfer!'),
      onSuccess: () => {
        notification['success']({
          message: 'Transfer finished!',
        });
        refetchTransfers();
      },
    }
  );

  return (
    <div className="transfer-map">
      <div className="transfer-map__content" ref={mapRef}>
        <OrganizationsMap
          organizations={preparedOrganizations}
          finishTransfer={finishTransfer}
          finishId={finishId}
        />
        <TruckMap
          organizations={preparedOrganizations}
          setFinishId={setFinishId}
          transfers={preparedTransfers}
          mapRef={mapRef}
          controlledTruckId={controlledTruckId}
          setControlledTruckId={setControlledTruckId}
        />
      </div>
    </div>
  );
};

export default TransferMap;
