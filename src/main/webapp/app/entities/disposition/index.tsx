import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Disposition from './disposition';
import DispositionDetail from './disposition-detail';
import DispositionUpdate from './disposition-update';
import DispositionDeleteDialog from './disposition-delete-dialog';

const DispositionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Disposition />} />
    <Route path="new" element={<DispositionUpdate />} />
    <Route path=":id">
      <Route index element={<DispositionDetail />} />
      <Route path="edit" element={<DispositionUpdate />} />
      <Route path="delete" element={<DispositionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DispositionRoutes;
