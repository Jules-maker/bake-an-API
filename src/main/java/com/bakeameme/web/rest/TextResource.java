package com.bakeameme.web.rest;

import com.bakeameme.domain.Text;
import com.bakeameme.repository.TextRepository;
import com.bakeameme.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.bakeameme.domain.Text}.
 */
@RestController
@RequestMapping("/api")
public class TextResource {

    private final Logger log = LoggerFactory.getLogger(TextResource.class);

    private static final String ENTITY_NAME = "text";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TextRepository textRepository;

    public TextResource(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    /**
     * {@code POST  /texts} : Create a new text.
     *
     * @param text the text to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new text, or with status {@code 400 (Bad Request)} if the text has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/texts")
    public ResponseEntity<Text> createText(@RequestBody Text text) throws URISyntaxException {
        log.debug("REST request to save Text : {}", text);
        if (text.getId() != null) {
            throw new BadRequestAlertException("A new text cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Text result = textRepository.save(text);
        return ResponseEntity
            .created(new URI("/api/texts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /texts/:id} : Updates an existing text.
     *
     * @param id the id of the text to save.
     * @param text the text to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated text,
     * or with status {@code 400 (Bad Request)} if the text is not valid,
     * or with status {@code 500 (Internal Server Error)} if the text couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/texts/{id}")
    public ResponseEntity<Text> updateText(@PathVariable(value = "id", required = false) final String id, @RequestBody Text text)
        throws URISyntaxException {
        log.debug("REST request to update Text : {}, {}", id, text);
        if (text.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, text.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!textRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Text result = textRepository.save(text);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, text.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /texts/:id} : Partial updates given fields of an existing text, field will ignore if it is null
     *
     * @param id the id of the text to save.
     * @param text the text to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated text,
     * or with status {@code 400 (Bad Request)} if the text is not valid,
     * or with status {@code 404 (Not Found)} if the text is not found,
     * or with status {@code 500 (Internal Server Error)} if the text couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/texts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Text> partialUpdateText(@PathVariable(value = "id", required = false) final String id, @RequestBody Text text)
        throws URISyntaxException {
        log.debug("REST request to partial update Text partially : {}, {}", id, text);
        if (text.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, text.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!textRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Text> result = textRepository
            .findById(text.getId())
            .map(existingText -> {
                if (text.getCoordinateX() != null) {
                    existingText.setCoordinateX(text.getCoordinateX());
                }
                if (text.getCoordinateY() != null) {
                    existingText.setCoordinateY(text.getCoordinateY());
                }
                if (text.getCreatedAt() != null) {
                    existingText.setCreatedAt(text.getCreatedAt());
                }
                if (text.getUpdatedAt() != null) {
                    existingText.setUpdatedAt(text.getUpdatedAt());
                }

                return existingText;
            })
            .map(textRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, text.getId()));
    }

    /**
     * {@code GET  /texts} : get all the texts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of texts in body.
     */
    @GetMapping("/texts")
    public ResponseEntity<List<Text>> getAllTexts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Texts");
        Page<Text> page = textRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /texts/:id} : get the "id" text.
     *
     * @param id the id of the text to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the text, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/texts/{id}")
    public ResponseEntity<Text> getText(@PathVariable String id) {
        log.debug("REST request to get Text : {}", id);
        Optional<Text> text = textRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(text);
    }

    /**
     * {@code DELETE  /texts/:id} : delete the "id" text.
     *
     * @param id the id of the text to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/texts/{id}")
    public ResponseEntity<Void> deleteText(@PathVariable String id) {
        log.debug("REST request to delete Text : {}", id);
        textRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
