package com.bakeanapi.web.rest;

import com.bakeanapi.domain.Disposition;
import com.bakeanapi.repository.DispositionRepository;
import com.bakeanapi.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bakeanapi.domain.Disposition}.
 */
@RestController
@RequestMapping("/api")
public class DispositionResource {

    private final Logger log = LoggerFactory.getLogger(DispositionResource.class);

    private static final String ENTITY_NAME = "disposition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DispositionRepository dispositionRepository;

    public DispositionResource(DispositionRepository dispositionRepository) {
        this.dispositionRepository = dispositionRepository;
    }

    /**
     * {@code POST  /dispositions} : Create a new disposition.
     *
     * @param disposition the disposition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disposition, or with status {@code 400 (Bad Request)} if the disposition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dispositions")
    public ResponseEntity<Disposition> createDisposition(@Valid @RequestBody Disposition disposition) throws URISyntaxException {
        log.debug("REST request to save Disposition : {}", disposition);
        if (disposition.getId() != null) {
            throw new BadRequestAlertException("A new disposition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Disposition result = dispositionRepository.save(disposition);
        return ResponseEntity
            .created(new URI("/api/dispositions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /dispositions/:id} : Updates an existing disposition.
     *
     * @param id the id of the disposition to save.
     * @param disposition the disposition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disposition,
     * or with status {@code 400 (Bad Request)} if the disposition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disposition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dispositions/{id}")
    public ResponseEntity<Disposition> updateDisposition(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Disposition disposition
    ) throws URISyntaxException {
        log.debug("REST request to update Disposition : {}, {}", id, disposition);
        if (disposition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disposition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Disposition result = dispositionRepository.save(disposition);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, disposition.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /dispositions/:id} : Partial updates given fields of an existing disposition, field will ignore if it is null
     *
     * @param id the id of the disposition to save.
     * @param disposition the disposition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disposition,
     * or with status {@code 400 (Bad Request)} if the disposition is not valid,
     * or with status {@code 404 (Not Found)} if the disposition is not found,
     * or with status {@code 500 (Internal Server Error)} if the disposition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dispositions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Disposition> partialUpdateDisposition(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Disposition disposition
    ) throws URISyntaxException {
        log.debug("REST request to partial update Disposition partially : {}, {}", id, disposition);
        if (disposition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disposition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Disposition> result = dispositionRepository
            .findById(disposition.getId())
            .map(existingDisposition -> {
                if (disposition.getName() != null) {
                    existingDisposition.setName(disposition.getName());
                }
                if (disposition.getSizeRequired() != null) {
                    existingDisposition.setSizeRequired(disposition.getSizeRequired());
                }
                if (disposition.getCoordinate1X() != null) {
                    existingDisposition.setCoordinate1X(disposition.getCoordinate1X());
                }
                if (disposition.getCoordinate1Y() != null) {
                    existingDisposition.setCoordinate1Y(disposition.getCoordinate1Y());
                }
                if (disposition.getCoordinate2X() != null) {
                    existingDisposition.setCoordinate2X(disposition.getCoordinate2X());
                }
                if (disposition.getCoordinate2Y() != null) {
                    existingDisposition.setCoordinate2Y(disposition.getCoordinate2Y());
                }
                if (disposition.getCoordinate3X() != null) {
                    existingDisposition.setCoordinate3X(disposition.getCoordinate3X());
                }
                if (disposition.getCoordinate3Y() != null) {
                    existingDisposition.setCoordinate3Y(disposition.getCoordinate3Y());
                }
                if (disposition.getCoordinate4X() != null) {
                    existingDisposition.setCoordinate4X(disposition.getCoordinate4X());
                }
                if (disposition.getCoordinate4Y() != null) {
                    existingDisposition.setCoordinate4Y(disposition.getCoordinate4Y());
                }
                if (disposition.getCreatedAt() != null) {
                    existingDisposition.setCreatedAt(disposition.getCreatedAt());
                }
                if (disposition.getUpdatedAt() != null) {
                    existingDisposition.setUpdatedAt(disposition.getUpdatedAt());
                }

                return existingDisposition;
            })
            .map(dispositionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, disposition.getId())
        );
    }

    /**
     * {@code GET  /dispositions} : get all the dispositions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dispositions in body.
     */
    @GetMapping("/dispositions")
    public ResponseEntity<List<Disposition>> getAllDispositions(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Dispositions");
        Page<Disposition> page = dispositionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dispositions/:id} : get the "id" disposition.
     *
     * @param id the id of the disposition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disposition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dispositions/{id}")
    public ResponseEntity<Disposition> getDisposition(@PathVariable String id) {
        log.debug("REST request to get Disposition : {}", id);
        Optional<Disposition> disposition = dispositionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(disposition);
    }

    /**
     * {@code DELETE  /dispositions/:id} : delete the "id" disposition.
     *
     * @param id the id of the disposition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dispositions/{id}")
    public ResponseEntity<Void> deleteDisposition(@PathVariable String id) {
        log.debug("REST request to delete Disposition : {}", id);
        dispositionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
