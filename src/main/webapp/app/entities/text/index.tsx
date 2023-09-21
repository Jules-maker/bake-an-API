import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Text from './text';
import TextDetail from './text-detail';
import TextUpdate from './text-update';
import TextDeleteDialog from './text-delete-dialog';

const TextRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Text />} />
    <Route path="new" element={<TextUpdate />} />
    <Route path=":id">
      <Route index element={<TextDetail />} />
      <Route path="edit" element={<TextUpdate />} />
      <Route path="delete" element={<TextDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TextRoutes;
