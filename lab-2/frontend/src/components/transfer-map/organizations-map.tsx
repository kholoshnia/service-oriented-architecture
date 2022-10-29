import { FC, useMemo } from 'react';

import { FlagOutlined } from '@ant-design/icons';
import { Popconfirm, Tooltip } from 'antd';
import { NumberParam, useQueryParam } from 'use-query-params';

import NewTransferModal from 'components/transfer-map/new-transfer-modal';
import { Organization, OrganizationId } from 'models/organization';
import { Normalized } from 'utils/map-helpers';
import { WithIcon } from 'utils/random-icon';

type OrganizationsMapProps = {
  organizations: Normalized<WithIcon<Organization>>[];
  finishId?: OrganizationId;
  finishTransfer: (organizationId: OrganizationId) => void;
};

const OrganizationsMap: FC<OrganizationsMapProps> = ({
  organizations,
  finishId,
  finishTransfer,
}) => {
  const [organizationId, setOrganizationId] = useQueryParam(
    'organization',
    NumberParam
  );

  const selectedOrganization = useMemo(
    () => organizations?.find(el => el.id === organizationId),
    [organizations, organizationId]
  );

  const getSize = (organization: Organization) =>
    Math.max(Math.min(String(organization.name).length * 10, 100), 50);

  return (
    <>
      {organizations.map(organization => (
        <div
          key={organization.id}
          className="transfer-map__organization"
          style={{
            top: organization.normalized.y,
            left: organization.normalized.x,
          }}
        >
          <Tooltip
            overlayClassName="store-tooltip"
            title={
              <div className="store-tooltip__content">
                <div>
                  <span>
                    <strong>Full name: </strong>
                    {organization.fullName || '<empty>'}
                  </span>
                </div>
                <div>
                  <span>
                    <strong>x: </strong>
                    {organization.coordinates.x}
                  </span>
                  <span>
                    <strong>y: </strong>
                    {organization.coordinates.y}
                  </span>
                </div>
              </div>
            }
          >
            <Popconfirm
              title="Finish transfer here?"
              icon={<FlagOutlined />}
              open={organization.id === finishId}
              onConfirm={() => finishTransfer(organization.id)}
              okText="Yes"
              showCancel={false}
            >
              <img
                style={{
                  width: getSize(organization),
                  height: getSize(organization),
                }}
                src={organization.iconSrc}
                alt={organization.name}
                onClick={() => setOrganizationId(organization.id)}
              />
            </Popconfirm>
          </Tooltip>
          <div className="transfer-map__organization-name">
            {organization.name}
          </div>
        </div>
      ))}
      {selectedOrganization && (
        <NewTransferModal
          open={!!selectedOrganization}
          close={() => setOrganizationId(undefined)}
          organization={selectedOrganization}
        />
      )}
    </>
  );
};

export default OrganizationsMap;
