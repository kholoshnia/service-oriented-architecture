import { Dispatch, FC, SetStateAction, useEffect, useMemo } from 'react';

import { useMutation } from '@tanstack/react-query';
import { Tooltip } from 'antd';
import { AxiosError } from 'axios';
import classNames from 'classnames';
import { NumberParam, useQueryParam } from 'use-query-params';

import truckTop from 'assets/images/truck-top.png';
import TruckInfoModal from 'components/transfer-map/truck-info-modal';
import { Coordinates, Organization, OrganizationId } from 'models/organization';
import { Transfer } from 'models/transfer';
import transferApi from 'services/storage/transfer-api';
import debounceDelay from 'utils/debounce-delay';
import { getInitialCoordinates, Normalized } from 'utils/map-helpers';
import { showError } from 'utils/server-error';
import { onKeyDownTruck, onKeyUpTruck } from 'utils/truck-controls';
import { WithIcon } from 'utils/with-icons';

type TruckMapProps = {
  organizations: Normalized<WithIcon<Organization>>[];
  setFinishId: Dispatch<SetStateAction<OrganizationId | undefined>>;
  transfers: Normalized<Transfer>[];
  mapRef: any;
  controlledTruck?: Normalized<Transfer>;
  setControlledTruck: (transfer: Normalized<Transfer>) => void;
};

const TruckMap: FC<TruckMapProps> = ({
  organizations,
  setFinishId,
  transfers,
  mapRef,
  controlledTruck,
  setControlledTruck,
}) => {
  const [transferId, setTransferId] = useQueryParam('truck', NumberParam);
  const selectedTransfer = useMemo(
    () => transfers?.find(el => el.id === transferId),
    [transfers, transferId]
  );

  const { mutate: updateCoordinates } = useMutation(
    ['update-coordinates'],
    (coordinates: Coordinates) =>
      transferApi.updateCoordinates(controlledTruck!.id, coordinates),
    {
      onError: (e: AxiosError) => showError(e, 'Could not update coordinates!'),
    }
  );

  const setNewCoordinates = debounceDelay(target => {
    if (!target) return;
    const truckX = parseFloat(target.style.left);
    const truckY = parseFloat(target.style.top);

    updateCoordinates(
      getInitialCoordinates(
        truckX,
        truckY,
        organizations,
        transfers,
        mapRef.current?.clientWidth,
        mapRef.current?.clientHeight
      )
    );

    const finish = organizations.find(
      o =>
        Math.abs(o.normalized.x - truckX) < 20 &&
        Math.abs(o.normalized.y - truckY) < 20
    );

    setFinishId(finish?.id);
  }, 500);

  const getStyle = (transfer: Normalized<Transfer>) =>
    transfer.id === controlledTruck?.id
      ? {
          top: controlledTruck?.normalized.y,
          left: controlledTruck?.normalized.x,
        }
      : {
          top: transfer.normalized.y,
          left: transfer.normalized.x,
        };

  useEffect(() => {
    const onKeyDown = event => {
      const target = document.getElementById(`truck-${controlledTruck?.id}`);
      onKeyDownTruck({
        event,
        target,
        maxX: mapRef.current?.clientWidth,
        maxY: mapRef.current?.clientHeight,
      });
      setNewCoordinates(target);
    };

    const onKeyUp = event => {
      const target = document.getElementById(`truck-${controlledTruck?.id}`);
      onKeyUpTruck({
        event,
        target,
        maxX: mapRef.current?.clientWidth,
        maxY: mapRef.current?.clientHeight,
      });
      setNewCoordinates(target);
    };

    document.addEventListener('keydown', onKeyDown);
    document.addEventListener('keyup', onKeyUp);
    return () => {
      document.removeEventListener('keydown', onKeyDown);
      document.removeEventListener('keyup', onKeyUp);
    };
  }, [controlledTruck]);

  return (
    <>
      {transfers.map(transfer => (
        <div
          id={`truck-${transfer.id}`}
          key={transfer.id}
          className="transfer-map__truck"
          style={getStyle(transfer)}
          onClick={() => setControlledTruck(transfer)}
          onDoubleClick={() => setTransferId(transfer.id)}
        >
          <Tooltip
            overlayClassName="store-tooltip"
            title={
              <div className="store-tooltip__content">
                <div>
                  <strong style={{ marginRight: '0.2rem' }}>From:</strong>
                  <span>"{transfer.sender.name}"</span>
                </div>
                <div>
                  <span>
                    <strong>x: </strong>
                    {transfer.coordinates.x}
                  </span>
                  <span>
                    <strong>y: </strong>
                    {transfer.coordinates.y}
                  </span>
                </div>
                <div>Double click for more</div>
              </div>
            }
          >
            <img
              className={classNames('transfer-map__truck-image', {
                'transfer-map__truck-image--controlled':
                  transfer.id === controlledTruck?.id,
              })}
              style={{
                width: 20,
              }}
              src={truckTop}
              alt="truck"
            />
          </Tooltip>
        </div>
      ))}
      {selectedTransfer && (
        <TruckInfoModal
          open={!!selectedTransfer}
          close={() => setTransferId(undefined)}
          transfer={selectedTransfer}
        />
      )}
    </>
  );
};

export default TruckMap;
