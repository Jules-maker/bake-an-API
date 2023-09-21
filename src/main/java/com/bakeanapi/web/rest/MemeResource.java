package com.bakeanapi.web.rest;

import com.bakeanapi.domain.Meme;
import com.bakeanapi.repository.MemeRepository;
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
 * REST controller for managing {@link com.bakeanapi.domain.Meme}.
 */
@RestController
@RequestMapping("/api")
public class MemeResource {

    private final Logger log = LoggerFactory.getLogger(MemeResource.class);

    private static final String ENTITY_NAME = "meme";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MemeRepository memeRepository;

    public MemeResource(MemeRepository memeRepository) {
        this.memeRepository = memeRepository;
    }

    /**
     * {@code POST  /memes} : Create a new meme.
     *
     * @param meme the meme to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meme, or with status {@code 400 (Bad Request)} if the meme has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/memes")
    public ResponseEntity<Meme> createMeme(@Valid @RequestBody Meme meme) throws URISyntaxException {
        log.debug("REST request to save Meme : {}", meme);
        if (meme.getId() != null) {
            throw new BadRequestAlertException("A new meme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Meme result = memeRepository.save(meme);
        return ResponseEntity
            .created(new URI("/api/memes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /memes/:id} : Updates an existing meme.
     *
     * @param id the id of the meme to save.
     * @param meme the meme to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meme,
     * or with status {@code 400 (Bad Request)} if the meme is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meme couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/memes/{id}")
    public ResponseEntity<Meme> updateMeme(@PathVariable(value = "id", required = false) final String id, @Valid @RequestBody Meme meme)
        throws URISyntaxException {
        log.debug("REST request to update Meme : {}, {}", id, meme);
        if (meme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, meme.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Meme result = memeRepository.save(meme);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, meme.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /memes/:id} : Partial updates given fields of an existing meme, field will ignore if it is null
     *
     * @param id the id of the meme to save.
     * @param meme the meme to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meme,
     * or with status {@code 400 (Bad Request)} if the meme is not valid,
     * or with status {@code 404 (Not Found)} if the meme is not found,
     * or with status {@code 500 (Internal Server Error)} if the meme couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/memes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Meme> partialUpdateMeme(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Meme meme
    ) throws URISyntaxException {
        log.debug("REST request to partial update Meme partially : {}, {}", id, meme);
        if (meme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, meme.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Meme> result = memeRepository
            .findById(meme.getId())
            .map(existingMeme -> {
                if (meme.getSlot() != null) {
                    existingMeme.setSlot(meme.getSlot());
                }
                if (meme.getTitle() != null) {
                    existingMeme.setTitle(meme.getTitle());
                }
                if (meme.getTag() != null) {
                    existingMeme.setTag(meme.getTag());
                }
                if (meme.getIsDraft() != null) {
                    existingMeme.setIsDraft(meme.getIsDraft());
                }
                if (meme.getCreatedAt() != null) {
                    existingMeme.setCreatedAt(meme.getCreatedAt());
                }
                if (meme.getUpdatedAt() != null) {
                    existingMeme.setUpdatedAt(meme.getUpdatedAt());
                }

                return existingMeme;
            })
            .map(memeRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, meme.getId()));
    }

    /**
     * {@code GET  /memes} : get all the memes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memes in body.
     */
    @GetMapping("/memes")
    public ResponseEntity<List<Meme>> getAllMemes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Memes");
        Page<Meme> page = memeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /memes/:id} : get the "id" meme.
     *
     * @param id the id of the meme to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meme, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/memes/{id}")
    public ResponseEntity<Meme> getMeme(@PathVariable String id) {
        log.debug("REST request to get Meme : {}", id);
        Optional<Meme> meme = memeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(meme);
    }

    /**
     * {@code DELETE  /memes/:id} : delete the "id" meme.
     *
     * @param id the id of the meme to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/memes/{id}")
    public ResponseEntity<Void> deleteMeme(@PathVariable String id) {
        log.debug("REST request to delete Meme : {}", id);
        memeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
