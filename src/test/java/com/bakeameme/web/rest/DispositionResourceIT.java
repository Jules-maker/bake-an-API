package com.bakeameme.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bakeameme.IntegrationTest;
import com.bakeameme.domain.Disposition;
import com.bakeameme.repository.DispositionRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link DispositionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DispositionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIZE_REQUIRED = 1;
    private static final Integer UPDATED_SIZE_REQUIRED = 2;

    private static final Integer DEFAULT_COORDINATE_1_X = 1;
    private static final Integer UPDATED_COORDINATE_1_X = 2;

    private static final Integer DEFAULT_COORDINATE_1_Y = 1;
    private static final Integer UPDATED_COORDINATE_1_Y = 2;

    private static final Integer DEFAULT_COORDINATE_2_X = 1;
    private static final Integer UPDATED_COORDINATE_2_X = 2;

    private static final Integer DEFAULT_COORDINATE_2_Y = 1;
    private static final Integer UPDATED_COORDINATE_2_Y = 2;

    private static final Integer DEFAULT_COORDINATE_3_X = 1;
    private static final Integer UPDATED_COORDINATE_3_X = 2;

    private static final Integer DEFAULT_COORDINATE_3_Y = 1;
    private static final Integer UPDATED_COORDINATE_3_Y = 2;

    private static final Integer DEFAULT_COORDINATE_4_X = 1;
    private static final Integer UPDATED_COORDINATE_4_X = 2;

    private static final Integer DEFAULT_COORDINATE_4_Y = 1;
    private static final Integer UPDATED_COORDINATE_4_Y = 2;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/dispositions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DispositionRepository dispositionRepository;

    @Autowired
    private MockMvc restDispositionMockMvc;

    private Disposition disposition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disposition createEntity() {
        Disposition disposition = new Disposition()
            .name(DEFAULT_NAME)
            .sizeRequired(DEFAULT_SIZE_REQUIRED)
            .coordinate1X(DEFAULT_COORDINATE_1_X)
            .coordinate1Y(DEFAULT_COORDINATE_1_Y)
            .coordinate2X(DEFAULT_COORDINATE_2_X)
            .coordinate2Y(DEFAULT_COORDINATE_2_Y)
            .coordinate3X(DEFAULT_COORDINATE_3_X)
            .coordinate3Y(DEFAULT_COORDINATE_3_Y)
            .coordinate4X(DEFAULT_COORDINATE_4_X)
            .coordinate4Y(DEFAULT_COORDINATE_4_Y)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return disposition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disposition createUpdatedEntity() {
        Disposition disposition = new Disposition()
            .name(UPDATED_NAME)
            .sizeRequired(UPDATED_SIZE_REQUIRED)
            .coordinate1X(UPDATED_COORDINATE_1_X)
            .coordinate1Y(UPDATED_COORDINATE_1_Y)
            .coordinate2X(UPDATED_COORDINATE_2_X)
            .coordinate2Y(UPDATED_COORDINATE_2_Y)
            .coordinate3X(UPDATED_COORDINATE_3_X)
            .coordinate3Y(UPDATED_COORDINATE_3_Y)
            .coordinate4X(UPDATED_COORDINATE_4_X)
            .coordinate4Y(UPDATED_COORDINATE_4_Y)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return disposition;
    }

    @BeforeEach
    public void initTest() {
        dispositionRepository.deleteAll();
        disposition = createEntity();
    }

    @Test
    void createDisposition() throws Exception {
        int databaseSizeBeforeCreate = dispositionRepository.findAll().size();
        // Create the Disposition
        restDispositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disposition)))
            .andExpect(status().isCreated());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeCreate + 1);
        Disposition testDisposition = dispositionList.get(dispositionList.size() - 1);
        assertThat(testDisposition.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDisposition.getSizeRequired()).isEqualTo(DEFAULT_SIZE_REQUIRED);
        assertThat(testDisposition.getCoordinate1X()).isEqualTo(DEFAULT_COORDINATE_1_X);
        assertThat(testDisposition.getCoordinate1Y()).isEqualTo(DEFAULT_COORDINATE_1_Y);
        assertThat(testDisposition.getCoordinate2X()).isEqualTo(DEFAULT_COORDINATE_2_X);
        assertThat(testDisposition.getCoordinate2Y()).isEqualTo(DEFAULT_COORDINATE_2_Y);
        assertThat(testDisposition.getCoordinate3X()).isEqualTo(DEFAULT_COORDINATE_3_X);
        assertThat(testDisposition.getCoordinate3Y()).isEqualTo(DEFAULT_COORDINATE_3_Y);
        assertThat(testDisposition.getCoordinate4X()).isEqualTo(DEFAULT_COORDINATE_4_X);
        assertThat(testDisposition.getCoordinate4Y()).isEqualTo(DEFAULT_COORDINATE_4_Y);
        assertThat(testDisposition.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDisposition.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    void createDispositionWithExistingId() throws Exception {
        // Create the Disposition with an existing ID
        disposition.setId("existing_id");

        int databaseSizeBeforeCreate = dispositionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disposition)))
            .andExpect(status().isBadRequest());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dispositionRepository.findAll().size();
        // set the field null
        disposition.setName(null);

        // Create the Disposition, which fails.

        restDispositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disposition)))
            .andExpect(status().isBadRequest());

        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkSizeRequiredIsRequired() throws Exception {
        int databaseSizeBeforeTest = dispositionRepository.findAll().size();
        // set the field null
        disposition.setSizeRequired(null);

        // Create the Disposition, which fails.

        restDispositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disposition)))
            .andExpect(status().isBadRequest());

        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllDispositions() throws Exception {
        // Initialize the database
        dispositionRepository.save(disposition);

        // Get all the dispositionList
        restDispositionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disposition.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sizeRequired").value(hasItem(DEFAULT_SIZE_REQUIRED)))
            .andExpect(jsonPath("$.[*].coordinate1X").value(hasItem(DEFAULT_COORDINATE_1_X)))
            .andExpect(jsonPath("$.[*].coordinate1Y").value(hasItem(DEFAULT_COORDINATE_1_Y)))
            .andExpect(jsonPath("$.[*].coordinate2X").value(hasItem(DEFAULT_COORDINATE_2_X)))
            .andExpect(jsonPath("$.[*].coordinate2Y").value(hasItem(DEFAULT_COORDINATE_2_Y)))
            .andExpect(jsonPath("$.[*].coordinate3X").value(hasItem(DEFAULT_COORDINATE_3_X)))
            .andExpect(jsonPath("$.[*].coordinate3Y").value(hasItem(DEFAULT_COORDINATE_3_Y)))
            .andExpect(jsonPath("$.[*].coordinate4X").value(hasItem(DEFAULT_COORDINATE_4_X)))
            .andExpect(jsonPath("$.[*].coordinate4Y").value(hasItem(DEFAULT_COORDINATE_4_Y)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    void getDisposition() throws Exception {
        // Initialize the database
        dispositionRepository.save(disposition);

        // Get the disposition
        restDispositionMockMvc
            .perform(get(ENTITY_API_URL_ID, disposition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disposition.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.sizeRequired").value(DEFAULT_SIZE_REQUIRED))
            .andExpect(jsonPath("$.coordinate1X").value(DEFAULT_COORDINATE_1_X))
            .andExpect(jsonPath("$.coordinate1Y").value(DEFAULT_COORDINATE_1_Y))
            .andExpect(jsonPath("$.coordinate2X").value(DEFAULT_COORDINATE_2_X))
            .andExpect(jsonPath("$.coordinate2Y").value(DEFAULT_COORDINATE_2_Y))
            .andExpect(jsonPath("$.coordinate3X").value(DEFAULT_COORDINATE_3_X))
            .andExpect(jsonPath("$.coordinate3Y").value(DEFAULT_COORDINATE_3_Y))
            .andExpect(jsonPath("$.coordinate4X").value(DEFAULT_COORDINATE_4_X))
            .andExpect(jsonPath("$.coordinate4Y").value(DEFAULT_COORDINATE_4_Y))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    void getNonExistingDisposition() throws Exception {
        // Get the disposition
        restDispositionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingDisposition() throws Exception {
        // Initialize the database
        dispositionRepository.save(disposition);

        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();

        // Update the disposition
        Disposition updatedDisposition = dispositionRepository.findById(disposition.getId()).get();
        updatedDisposition
            .name(UPDATED_NAME)
            .sizeRequired(UPDATED_SIZE_REQUIRED)
            .coordinate1X(UPDATED_COORDINATE_1_X)
            .coordinate1Y(UPDATED_COORDINATE_1_Y)
            .coordinate2X(UPDATED_COORDINATE_2_X)
            .coordinate2Y(UPDATED_COORDINATE_2_Y)
            .coordinate3X(UPDATED_COORDINATE_3_X)
            .coordinate3Y(UPDATED_COORDINATE_3_Y)
            .coordinate4X(UPDATED_COORDINATE_4_X)
            .coordinate4Y(UPDATED_COORDINATE_4_Y)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restDispositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDisposition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDisposition))
            )
            .andExpect(status().isOk());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
        Disposition testDisposition = dispositionList.get(dispositionList.size() - 1);
        assertThat(testDisposition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDisposition.getSizeRequired()).isEqualTo(UPDATED_SIZE_REQUIRED);
        assertThat(testDisposition.getCoordinate1X()).isEqualTo(UPDATED_COORDINATE_1_X);
        assertThat(testDisposition.getCoordinate1Y()).isEqualTo(UPDATED_COORDINATE_1_Y);
        assertThat(testDisposition.getCoordinate2X()).isEqualTo(UPDATED_COORDINATE_2_X);
        assertThat(testDisposition.getCoordinate2Y()).isEqualTo(UPDATED_COORDINATE_2_Y);
        assertThat(testDisposition.getCoordinate3X()).isEqualTo(UPDATED_COORDINATE_3_X);
        assertThat(testDisposition.getCoordinate3Y()).isEqualTo(UPDATED_COORDINATE_3_Y);
        assertThat(testDisposition.getCoordinate4X()).isEqualTo(UPDATED_COORDINATE_4_X);
        assertThat(testDisposition.getCoordinate4Y()).isEqualTo(UPDATED_COORDINATE_4_Y);
        assertThat(testDisposition.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDisposition.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void putNonExistingDisposition() throws Exception {
        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();
        disposition.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, disposition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disposition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDisposition() throws Exception {
        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();
        disposition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disposition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDisposition() throws Exception {
        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();
        disposition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disposition)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDispositionWithPatch() throws Exception {
        // Initialize the database
        dispositionRepository.save(disposition);

        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();

        // Update the disposition using partial update
        Disposition partialUpdatedDisposition = new Disposition();
        partialUpdatedDisposition.setId(disposition.getId());

        partialUpdatedDisposition.sizeRequired(UPDATED_SIZE_REQUIRED).coordinate4Y(UPDATED_COORDINATE_4_Y).updatedAt(UPDATED_UPDATED_AT);

        restDispositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisposition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDisposition))
            )
            .andExpect(status().isOk());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
        Disposition testDisposition = dispositionList.get(dispositionList.size() - 1);
        assertThat(testDisposition.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDisposition.getSizeRequired()).isEqualTo(UPDATED_SIZE_REQUIRED);
        assertThat(testDisposition.getCoordinate1X()).isEqualTo(DEFAULT_COORDINATE_1_X);
        assertThat(testDisposition.getCoordinate1Y()).isEqualTo(DEFAULT_COORDINATE_1_Y);
        assertThat(testDisposition.getCoordinate2X()).isEqualTo(DEFAULT_COORDINATE_2_X);
        assertThat(testDisposition.getCoordinate2Y()).isEqualTo(DEFAULT_COORDINATE_2_Y);
        assertThat(testDisposition.getCoordinate3X()).isEqualTo(DEFAULT_COORDINATE_3_X);
        assertThat(testDisposition.getCoordinate3Y()).isEqualTo(DEFAULT_COORDINATE_3_Y);
        assertThat(testDisposition.getCoordinate4X()).isEqualTo(DEFAULT_COORDINATE_4_X);
        assertThat(testDisposition.getCoordinate4Y()).isEqualTo(UPDATED_COORDINATE_4_Y);
        assertThat(testDisposition.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDisposition.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void fullUpdateDispositionWithPatch() throws Exception {
        // Initialize the database
        dispositionRepository.save(disposition);

        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();

        // Update the disposition using partial update
        Disposition partialUpdatedDisposition = new Disposition();
        partialUpdatedDisposition.setId(disposition.getId());

        partialUpdatedDisposition
            .name(UPDATED_NAME)
            .sizeRequired(UPDATED_SIZE_REQUIRED)
            .coordinate1X(UPDATED_COORDINATE_1_X)
            .coordinate1Y(UPDATED_COORDINATE_1_Y)
            .coordinate2X(UPDATED_COORDINATE_2_X)
            .coordinate2Y(UPDATED_COORDINATE_2_Y)
            .coordinate3X(UPDATED_COORDINATE_3_X)
            .coordinate3Y(UPDATED_COORDINATE_3_Y)
            .coordinate4X(UPDATED_COORDINATE_4_X)
            .coordinate4Y(UPDATED_COORDINATE_4_Y)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restDispositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisposition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDisposition))
            )
            .andExpect(status().isOk());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
        Disposition testDisposition = dispositionList.get(dispositionList.size() - 1);
        assertThat(testDisposition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDisposition.getSizeRequired()).isEqualTo(UPDATED_SIZE_REQUIRED);
        assertThat(testDisposition.getCoordinate1X()).isEqualTo(UPDATED_COORDINATE_1_X);
        assertThat(testDisposition.getCoordinate1Y()).isEqualTo(UPDATED_COORDINATE_1_Y);
        assertThat(testDisposition.getCoordinate2X()).isEqualTo(UPDATED_COORDINATE_2_X);
        assertThat(testDisposition.getCoordinate2Y()).isEqualTo(UPDATED_COORDINATE_2_Y);
        assertThat(testDisposition.getCoordinate3X()).isEqualTo(UPDATED_COORDINATE_3_X);
        assertThat(testDisposition.getCoordinate3Y()).isEqualTo(UPDATED_COORDINATE_3_Y);
        assertThat(testDisposition.getCoordinate4X()).isEqualTo(UPDATED_COORDINATE_4_X);
        assertThat(testDisposition.getCoordinate4Y()).isEqualTo(UPDATED_COORDINATE_4_Y);
        assertThat(testDisposition.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDisposition.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void patchNonExistingDisposition() throws Exception {
        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();
        disposition.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, disposition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(disposition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDisposition() throws Exception {
        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();
        disposition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(disposition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDisposition() throws Exception {
        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();
        disposition.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(disposition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDisposition() throws Exception {
        // Initialize the database
        dispositionRepository.save(disposition);

        int databaseSizeBeforeDelete = dispositionRepository.findAll().size();

        // Delete the disposition
        restDispositionMockMvc
            .perform(delete(ENTITY_API_URL_ID, disposition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
