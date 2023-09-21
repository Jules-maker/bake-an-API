import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './disposition.reducer';

export const DispositionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dispositionEntity = useAppSelector(state => state.disposition.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dispositionDetailsHeading">Disposition</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{dispositionEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{dispositionEntity.name}</dd>
          <dt>
            <span id="sizeRequired">Size Required</span>
          </dt>
          <dd>{dispositionEntity.sizeRequired}</dd>
          <dt>
            <span id="coordinate1X">Coordinate 1 X</span>
          </dt>
          <dd>{dispositionEntity.coordinate1X}</dd>
          <dt>
            <span id="coordinate1Y">Coordinate 1 Y</span>
          </dt>
          <dd>{dispositionEntity.coordinate1Y}</dd>
          <dt>
            <span id="coordinate2X">Coordinate 2 X</span>
          </dt>
          <dd>{dispositionEntity.coordinate2X}</dd>
          <dt>
            <span id="coordinate2Y">Coordinate 2 Y</span>
          </dt>
          <dd>{dispositionEntity.coordinate2Y}</dd>
          <dt>
            <span id="coordinate3X">Coordinate 3 X</span>
          </dt>
          <dd>{dispositionEntity.coordinate3X}</dd>
          <dt>
            <span id="coordinate3Y">Coordinate 3 Y</span>
          </dt>
          <dd>{dispositionEntity.coordinate3Y}</dd>
          <dt>
            <span id="coordinate4X">Coordinate 4 X</span>
          </dt>
          <dd>{dispositionEntity.coordinate4X}</dd>
          <dt>
            <span id="coordinate4Y">Coordinate 4 Y</span>
          </dt>
          <dd>{dispositionEntity.coordinate4Y}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {dispositionEntity.createdAt ? <TextFormat value={dispositionEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {dispositionEntity.updatedAt ? <TextFormat value={dispositionEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Meme Id</dt>
          <dd>{dispositionEntity.memeId ? dispositionEntity.memeId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/disposition" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/disposition/${dispositionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DispositionDetail;
