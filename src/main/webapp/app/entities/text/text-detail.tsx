import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './text.reducer';

export const TextDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const textEntity = useAppSelector(state => state.text.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="textDetailsHeading">Text</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{textEntity.id}</dd>
          <dt>
            <span id="coordinateX">Coordinate X</span>
          </dt>
          <dd>{textEntity.coordinateX}</dd>
          <dt>
            <span id="coordinateY">Coordinate Y</span>
          </dt>
          <dd>{textEntity.coordinateY}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{textEntity.createdAt ? <TextFormat value={textEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>{textEntity.updatedAt ? <TextFormat value={textEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Image Id</dt>
          <dd>{textEntity.imageId ? textEntity.imageId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/text" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/text/${textEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TextDetail;
