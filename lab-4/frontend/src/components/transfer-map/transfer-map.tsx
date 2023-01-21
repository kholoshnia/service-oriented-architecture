import { FC, useEffect, useRef, useState } from 'react';
import './transfer-map.scss';

import { useMutation } from '@tanstack/react-query';
import { notification } from 'antd';
import { AxiosError } from 'axios';

import OrganizationsMap from 'components/transfer-map/organizations-map';
import TruckMap from 'components/transfer-map/truck-map';
import { Organization, OrganizationId } from 'models/organization';
import { Transfer } from 'models/transfer';
import transferApi from 'services/storage/transfer-api';
import { normalizeCoordinates, Normalized } from 'utils/map-helpers';
import { showError } from 'utils/server-error';
import withIcons, { WithIcon } from 'utils/with-icons';

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
  const [preparedTransfers, setPreparedTransfers] = useState<
    Normalized<Transfer>[]
  >([]);

  useEffect(() => {
    const normalizeData = () => {
      const [os, ts] = normalizeCoordinates(
        withIcons(organizations),
        transfers,
        mapRef.current?.clientWidth,
        mapRef.current?.clientHeight
      );
      setPreparedOrganizations(os);
      setPreparedTransfers(ts);
    };

    normalizeData();
    window.addEventListener('resize', normalizeData);
    return () => window.removeEventListener('resize', normalizeData);
  }, [organizations, transfers]);

  const [finishId, setFinishId] = useState<OrganizationId>();

  const [controlledTruck, setControlledTruck] =
    useState<Normalized<Transfer>>();

  const { mutate: finishTransfer } = useMutation(
    ['finish-transfer'],
    (organizationId: OrganizationId) =>
      controlledTruck?.id
        ? transferApi.finishTransfer(controlledTruck.id, organizationId)
        : Promise.resolve(undefined),
    {
      onError: (e: AxiosError) => showError(e, 'Could not finish transfer!'),
      onSuccess: () => {
        notification['success']({
          message: 'Transfer finished!',
        });
        setFinishId(undefined);
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
          refetchTransfers={refetchTransfers}
          setControlledTruck={setControlledTruck}
        />
        <TruckMap
          organizations={preparedOrganizations}
          setFinishId={setFinishId}
          transfers={preparedTransfers}
          mapRef={mapRef}
          controlledTruck={controlledTruck}
          setControlledTruck={setControlledTruck}
        />
      </div>
    </div>
  );
};

export default TransferMap;
