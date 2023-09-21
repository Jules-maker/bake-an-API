import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Meme from './meme';
import Image from './image';
import Text from './text';
import Disposition from './disposition';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="meme/*" element={<Meme />} />
        <Route path="image/*" element={<Image />} />
        <Route path="text/*" element={<Text />} />
        <Route path="disposition/*" element={<Disposition />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
