import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Meme from './meme';
import MemeDetail from './meme-detail';
import MemeUpdate from './meme-update';
import MemeDeleteDialog from './meme-delete-dialog';

const MemeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Meme />} />
    <Route path="new" element={<MemeUpdate />} />
    <Route path=":id">
      <Route index element={<MemeDetail />} />
      <Route path="edit" element={<MemeUpdate />} />
      <Route path="delete" element={<MemeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MemeRoutes;
