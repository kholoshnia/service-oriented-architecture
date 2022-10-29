import { Dispatch, FC, SetStateAction, useMemo } from 'react';

import { Tooltip } from 'antd';
import classNames from 'classnames';
import { NumberParam, useQueryParam } from 'use-query-params';

import truckTop from 'assets/images/truck-top.png';
import TruckInfoModal from 'components/transfer-map/truck-info-modal';
import { Organization, OrganizationId } from 'models/organization';
import { Transfer, TransferId } from 'models/transfer';
import debounce from 'utils/debounce';
import { Normalized } from 'utils/map-helpers';
import { WithIcon } from 'utils/random-icon';
import { onKeyDownTruck, onKeyUpTruck } from 'utils/truck-controls';

type TruckMapProps = {
  organizations: Normalized<WithIcon<Organization>>[];
  setFinishId: Dispatch<SetStateAction<OrganizationId | undefined>>;
  transfers: Normalized<Transfer>[];
  mapRef: any;
  controlledTruckId?: number | null;
  setControlledTruckId: (transferId: TransferId) => void;
};

const TruckMap: FC<TruckMapProps> = ({
  organizations,
  setFinishId,
  transfers,
  mapRef,
  controlledTruckId,
  setControlledTruckId,
}) => {
  const [transferId, setTransferId] = useQueryParam('truck', NumberParam);
  const selectedTransfer = useMemo(
    () => transfers?.find(el => el.id === transferId),
    [transfers, transferId]
  );

  const setNewCoordinates = debounce((e, transfer: Transfer) => {
    const truckX = parseFloat(e.target.style.left);
    const truckY = parseFloat(e.target.style.top);

    const finish = organizations.find(
      o =>
        Math.abs(o.normalized.x - truckX) < 20 &&
        Math.abs(o.normalized.y - truckY) < 20
    );

    setFinishId(finish?.id);
  }, 1000);

  return (
    <>
      {transfers.map(transfer => (
        <div
          id={`truck-${transfer.id}`}
          key={transfer.id}
          className="transfer-map__truck"
          style={{
            top: transfer.normalized.y,
            left: transfer.normalized.x,
          }}
          tabIndex={transfer.id}
          onKeyDown={e => {
            onKeyDownTruck(
              e,
              mapRef.current?.clientWidth,
              mapRef.current?.clientHeight
            );
            setNewCoordinates(e, transfer);
          }}
          onKeyUp={e => {
            onKeyUpTruck(e);
            setNewCoordinates(e, transfer);
          }}
        >
          <Tooltip
            overlayClassName="store-tooltip"
            title={
              <div className="store-tooltip__content">
                <div className="transfer-map__organization-name">
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
                'transfer-map__controlled-truck':
                  transfer.id === controlledTruckId,
              })}
              style={{
                width: 20,
              }}
              src={truckTop}
              alt="truck"
              onClick={() => setControlledTruckId(transfer.id)}
              onDoubleClick={() => setTransferId(transfer.id)}
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
