import { Coordinates, OrganizationId } from 'models/organization';
import { NewTransfer, Transfer, TransferId } from 'models/transfer';
import { Page, PaginationParams } from 'models/utils';
import { storageClient } from 'utils/axios-clients';

const transferApi = {
  getTransfers: (
    paginationParams?: PaginationParams,
    includeFinished?: Boolean
  ) =>
    storageClient.get<Page<Transfer>>('/transfers', {
      params: { ...paginationParams, includeFinished },
    }),

  addTransfer: (newTransfer: NewTransfer) =>
    storageClient.post<Transfer>('/transfers', newTransfer),

  updateCoordinates: (transferId: TransferId, coordinates: Coordinates) =>
    storageClient.put<Transfer>(
      `/transfers/${transferId}/coordinates`,
      coordinates
    ),

  finishTransfer: (transferId: TransferId, organizationId: OrganizationId) =>
    storageClient.put<Transfer>(
      `/transfers/${transferId}/finish/${organizationId}`
    ),
};

export default transferApi;
