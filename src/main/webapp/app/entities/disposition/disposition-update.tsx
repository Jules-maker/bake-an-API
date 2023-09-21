import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMeme } from 'app/shared/model/meme.model';
import { getEntities as getMemes } from 'app/entities/meme/meme.reducer';
import { IDisposition } from 'app/shared/model/disposition.model';
import { getEntity, updateEntity, createEntity, reset } from './disposition.reducer';

export const DispositionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const memes = useAppSelector(state => state.meme.entities);
  const dispositionEntity = useAppSelector(state => state.disposition.entity);
  const loading = useAppSelector(state => state.disposition.loading);
  const updating = useAppSelector(state => state.disposition.updating);
  const updateSuccess = useAppSelector(state => state.disposition.updateSuccess);

  const handleClose = () => {
    navigate('/disposition');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getMemes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

    const entity = {
      ...dispositionEntity,
      ...values,
      memeId: memes.find(it => it.id.toString() === values.memeId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
        }
      : {
          ...dispositionEntity,
          createdAt: convertDateTimeFromServer(dispositionEntity.createdAt),
          updatedAt: convertDateTimeFromServer(dispositionEntity.updatedAt),
          memeId: dispositionEntity?.memeId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="bakeAnApiApp.disposition.home.createOrEditLabel" data-cy="DispositionCreateUpdateHeading">
            Créer ou éditer un Disposition
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="disposition-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="disposition-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'Ce champ est obligatoire.' },
                }}
              />
              <ValidatedField
                label="Size Required"
                id="disposition-sizeRequired"
                name="sizeRequired"
                data-cy="sizeRequired"
                type="text"
                validate={{
                  required: { value: true, message: 'Ce champ est obligatoire.' },
                  validate: v => isNumber(v) || 'Ce champ doit être un nombre.',
                }}
              />
              <ValidatedField label="Coordinate 1 X" id="disposition-coordinate1X" name="coordinate1X" data-cy="coordinate1X" type="text" />
              <ValidatedField label="Coordinate 1 Y" id="disposition-coordinate1Y" name="coordinate1Y" data-cy="coordinate1Y" type="text" />
              <ValidatedField label="Coordinate 2 X" id="disposition-coordinate2X" name="coordinate2X" data-cy="coordinate2X" type="text" />
              <ValidatedField label="Coordinate 2 Y" id="disposition-coordinate2Y" name="coordinate2Y" data-cy="coordinate2Y" type="text" />
              <ValidatedField label="Coordinate 3 X" id="disposition-coordinate3X" name="coordinate3X" data-cy="coordinate3X" type="text" />
              <ValidatedField label="Coordinate 3 Y" id="disposition-coordinate3Y" name="coordinate3Y" data-cy="coordinate3Y" type="text" />
              <ValidatedField label="Coordinate 4 X" id="disposition-coordinate4X" name="coordinate4X" data-cy="coordinate4X" type="text" />
              <ValidatedField label="Coordinate 4 Y" id="disposition-coordinate4Y" name="coordinate4Y" data-cy="coordinate4Y" type="text" />
              <ValidatedField
                label="Created At"
                id="disposition-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updated At"
                id="disposition-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="disposition-memeId" name="memeId" data-cy="memeId" label="Meme Id" type="select">
                <option value="" key="0" />
                {memes
                  ? memes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/disposition" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Retour</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Sauvegarder
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default DispositionUpdate;
