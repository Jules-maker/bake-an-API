import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './image.reducer';

export const ImageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const imageEntity = useAppSelector(state => state.image.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="imageDetailsHeading">Image</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{imageEntity.id}</dd>
          <dt>
            <span id="position">Position</span>
          </dt>
          <dd>{imageEntity.position}</dd>
          <dt>
            <span id="url">Url</span>
          </dt>
          <dd>{imageEntity.url}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{imageEntity.createdAt ? <TextFormat value={imageEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>{imageEntity.updatedAt ? <TextFormat value={imageEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Meme Id</dt>
          <dd>{imageEntity.memeId ? imageEntity.memeId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/image" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/image/${imageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ImageDetail;
