import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMeme } from 'app/shared/model/meme.model';
import { Tags } from 'app/shared/model/enumerations/tags.model';
import { getEntity, updateEntity, createEntity, reset } from './meme.reducer';

export const MemeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const memeEntity = useAppSelector(state => state.meme.entity);
  const loading = useAppSelector(state => state.meme.loading);
  const updating = useAppSelector(state => state.meme.updating);
  const updateSuccess = useAppSelector(state => state.meme.updateSuccess);
  const tagsValues = Object.keys(Tags);

  const handleClose = () => {
    navigate('/meme');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
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
      ...memeEntity,
      ...values,
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
          tag: 'CLASSIQUES',
          ...memeEntity,
          createdAt: convertDateTimeFromServer(memeEntity.createdAt),
          updatedAt: convertDateTimeFromServer(memeEntity.updatedAt),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="bakeamemenfsApp.meme.home.createOrEditLabel" data-cy="MemeCreateUpdateHeading">
            Créer ou éditer un Meme
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="meme-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Slot"
                id="meme-slot"
                name="slot"
                data-cy="slot"
                type="text"
                validate={{
                  required: { value: true, message: 'Ce champ est obligatoire.' },
                  validate: v => isNumber(v) || 'Ce champ doit être un nombre.',
                }}
              />
              <ValidatedField label="Title" id="meme-title" name="title" data-cy="title" type="text" />
              <ValidatedField label="Tag" id="meme-tag" name="tag" data-cy="tag" type="select">
                {tagsValues.map(tags => (
                  <option value={tags} key={tags}>
                    {tags}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Is Draft" id="meme-isDraft" name="isDraft" data-cy="isDraft" check type="checkbox" />
              <ValidatedField
                label="Created At"
                id="meme-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updated At"
                id="meme-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/meme" replace color="info">
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

export default MemeUpdate;
