import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/meme">
        Meme
      </MenuItem>
      <MenuItem icon="asterisk" to="/image">
        Image
      </MenuItem>
      <MenuItem icon="asterisk" to="/text">
        Text
      </MenuItem>
      <MenuItem icon="asterisk" to="/disposition">
        Disposition
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
