import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDisposition } from 'app/shared/model/disposition.model';
import { getEntities, reset } from './disposition.reducer';

export const Disposition = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const dispositionList = useAppSelector(state => state.disposition.entities);
  const loading = useAppSelector(state => state.disposition.loading);
  const totalItems = useAppSelector(state => state.disposition.totalItems);
  const links = useAppSelector(state => state.disposition.links);
  const entity = useAppSelector(state => state.disposition.entity);
  const updateSuccess = useAppSelector(state => state.disposition.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  return (
    <div>
      <h2 id="disposition-heading" data-cy="DispositionHeading">
        Dispositions
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Actualiser la liste
          </Button>
          <Link to="/disposition/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Créer un nouveau Disposition
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={dispositionList ? dispositionList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {dispositionList && dispositionList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('sizeRequired')}>
                    Size Required <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('coordinate1X')}>
                    Coordinate 1 X <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('coordinate1Y')}>
                    Coordinate 1 Y <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('coordinate2X')}>
                    Coordinate 2 X <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('coordinate2Y')}>
                    Coordinate 2 Y <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('coordinate3X')}>
                    Coordinate 3 X <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('coordinate3Y')}>
                    Coordinate 3 Y <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('coordinate4X')}>
                    Coordinate 4 X <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('coordinate4Y')}>
                    Coordinate 4 Y <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdAt')}>
                    Created At <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedAt')}>
                    Updated At <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Meme Id <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {dispositionList.map((disposition, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/disposition/${disposition.id}`} color="link" size="sm">
                        {disposition.id}
                      </Button>
                    </td>
                    <td>{disposition.name}</td>
                    <td>{disposition.sizeRequired}</td>
                    <td>{disposition.coordinate1X}</td>
                    <td>{disposition.coordinate1Y}</td>
                    <td>{disposition.coordinate2X}</td>
                    <td>{disposition.coordinate2Y}</td>
                    <td>{disposition.coordinate3X}</td>
                    <td>{disposition.coordinate3Y}</td>
                    <td>{disposition.coordinate4X}</td>
                    <td>{disposition.coordinate4Y}</td>
                    <td>
                      {disposition.createdAt ? <TextFormat type="date" value={disposition.createdAt} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {disposition.updatedAt ? <TextFormat type="date" value={disposition.updatedAt} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{disposition.memeId ? <Link to={`/meme/${disposition.memeId.id}`}>{disposition.memeId.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/disposition/${disposition.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Voir</span>
                        </Button>
                        <Button tag={Link} to={`/disposition/${disposition.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/disposition/${disposition.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Supprimer</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">Aucun Disposition trouvé</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Disposition;
