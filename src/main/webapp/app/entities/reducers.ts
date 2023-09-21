import meme from 'app/entities/meme/meme.reducer';
import image from 'app/entities/image/image.reducer';
import text from 'app/entities/text/text.reducer';
import disposition from 'app/entities/disposition/disposition.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  meme,
  image,
  text,
  disposition,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
