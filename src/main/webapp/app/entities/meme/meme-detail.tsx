import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './meme.reducer';

export const MemeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const memeEntity = useAppSelector(state => state.meme.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="memeDetailsHeading">Meme</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{memeEntity.id}</dd>
          <dt>
            <span id="slot">Slot</span>
          </dt>
          <dd>{memeEntity.slot}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{memeEntity.title}</dd>
          <dt>
            <span id="tag">Tag</span>
          </dt>
          <dd>{memeEntity.tag}</dd>
          <dt>
            <span id="isDraft">Is Draft</span>
          </dt>
          <dd>{memeEntity.isDraft ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{memeEntity.createdAt ? <TextFormat value={memeEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>{memeEntity.updatedAt ? <TextFormat value={memeEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/meme" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/meme/${memeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MemeDetail;
