package com.bakeameme.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bakeameme.IntegrationTest;
import com.bakeameme.domain.Meme;
import com.bakeameme.domain.enumeration.Tags;
import com.bakeameme.repository.MemeRepository;
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
 * Integration tests for the {@link MemeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemeResourceIT {

    private static final Integer DEFAULT_SLOT = 1;
    private static final Integer UPDATED_SLOT = 2;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Tags DEFAULT_TAG = Tags.CLASSIQUES;
    private static final Tags UPDATED_TAG = Tags.POLITIQUES;

    private static final Boolean DEFAULT_IS_DRAFT = false;
    private static final Boolean UPDATED_IS_DRAFT = true;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/memes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private MemeRepository memeRepository;

    @Autowired
    private MockMvc restMemeMockMvc;

    private Meme meme;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meme createEntity() {
        Meme meme = new Meme()
            .slot(DEFAULT_SLOT)
            .title(DEFAULT_TITLE)
            .tag(DEFAULT_TAG)
            .isDraft(DEFAULT_IS_DRAFT)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return meme;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meme createUpdatedEntity() {
        Meme meme = new Meme()
            .slot(UPDATED_SLOT)
            .title(UPDATED_TITLE)
            .tag(UPDATED_TAG)
            .isDraft(UPDATED_IS_DRAFT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return meme;
    }

    @BeforeEach
    public void initTest() {
        memeRepository.deleteAll();
        meme = createEntity();
    }

    @Test
    void createMeme() throws Exception {
        int databaseSizeBeforeCreate = memeRepository.findAll().size();
        // Create the Meme
        restMemeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(meme)))
            .andExpect(status().isCreated());

        // Validate the Meme in the database
        List<Meme> memeList = memeRepository.findAll();
        assertThat(memeList).hasSize(databaseSizeBeforeCreate + 1);
        Meme testMeme = memeList.get(memeList.size() - 1);
        assertThat(testMeme.getSlot()).isEqualTo(DEFAULT_SLOT);
        assertThat(testMeme.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMeme.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testMeme.getIsDraft()).isEqualTo(DEFAULT_IS_DRAFT);
        assertThat(testMeme.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testMeme.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    void createMemeWithExistingId() throws Exception {
        // Create the Meme with an existing ID
        meme.setId("existing_id");

        int databaseSizeBeforeCreate = memeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(meme)))
            .andExpect(status().isBadRequest());

        // Validate the Meme in the database
        List<Meme> memeList = memeRepository.findAll();
        assertThat(memeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkSlotIsRequired() throws Exception {
        int databaseSizeBeforeTest = memeRepository.findAll().size();
        // set the field null
        meme.setSlot(null);

        // Create the Meme, which fails.

        restMemeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(meme)))
            .andExpect(status().isBadRequest());

        List<Meme> memeList = memeRepository.findAll();
        assertThat(memeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllMemes() throws Exception {
        // Initialize the database
        memeRepository.save(meme);

        // Get all the memeList
        restMemeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meme.getId())))
            .andExpect(jsonPath("$.[*].slot").value(hasItem(DEFAULT_SLOT)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())))
            .andExpect(jsonPath("$.[*].isDraft").value(hasItem(DEFAULT_IS_DRAFT.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    void getMeme() throws Exception {
        // Initialize the database
        memeRepository.save(meme);

        // Get the meme
        restMemeMockMvc
            .perform(get(ENTITY_API_URL_ID, meme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(meme.getId()))
            .andExpect(jsonPath("$.slot").value(DEFAULT_SLOT))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()))
            .andExpect(jsonPath("$.isDraft").value(DEFAULT_IS_DRAFT.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    void getNonExistingMeme() throws Exception {
        // Get the meme
        restMemeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingMeme() throws Exception {
        // Initialize the database
        memeRepository.save(meme);

        int databaseSizeBeforeUpdate = memeRepository.findAll().size();

        // Update the meme
        Meme updatedMeme = memeRepository.findById(meme.getId()).get();
        updatedMeme
            .slot(UPDATED_SLOT)
            .title(UPDATED_TITLE)
            .tag(UPDATED_TAG)
            .isDraft(UPDATED_IS_DRAFT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restMemeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMeme.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMeme))
            )
            .andExpect(status().isOk());

        // Validate the Meme in the database
        List<Meme> memeList = memeRepository.findAll();
        assertThat(memeList).hasSize(databaseSizeBeforeUpdate);
        Meme testMeme = memeList.get(memeList.size() - 1);
        assertThat(testMeme.getSlot()).isEqualTo(UPDATED_SLOT);
        assertThat(testMeme.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMeme.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testMeme.getIsDraft()).isEqualTo(UPDATED_IS_DRAFT);
        assertThat(testMeme.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testMeme.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void putNonExistingMeme() throws Exception {
        int databaseSizeBeforeUpdate = memeRepository.findAll().size();
        meme.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, meme.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(meme))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meme in the database
        List<Meme> memeList = memeRepository.findAll();
        assertThat(memeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMeme() throws Exception {
        int databaseSizeBeforeUpdate = memeRepository.findAll().size();
        meme.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(meme))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meme in the database
        List<Meme> memeList = memeRepository.findAll();
        assertThat(memeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMeme() throws Exception {
        int databaseSizeBeforeUpdate = memeRepository.findAll().size();
        meme.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(meme)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Meme in the database
        List<Meme> memeList = memeRepository.findAll();
        assertThat(memeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMemeWithPatch() throws Exception {
        // Initialize the database
        memeRepository.save(meme);

        int databaseSizeBeforeUpdate = memeRepository.findAll().size();

        // Update the meme using partial update
        Meme partialUpdatedMeme = new Meme();
        partialUpdatedMeme.setId(meme.getId());

        partialUpdatedMeme.slot(UPDATED_SLOT).title(UPDATED_TITLE).tag(UPDATED_TAG);

        restMemeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeme.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMeme))
            )
            .andExpect(status().isOk());

        // Validate the Meme in the database
        List<Meme> memeList = memeRepository.findAll();
        assertThat(memeList).hasSize(databaseSizeBeforeUpdate);
        Meme testMeme = memeList.get(memeList.size() - 1);
        assertThat(testMeme.getSlot()).isEqualTo(UPDATED_SLOT);
        assertThat(testMeme.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMeme.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testMeme.getIsDraft()).isEqualTo(DEFAULT_IS_DRAFT);
        assertThat(testMeme.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testMeme.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    void fullUpdateMemeWithPatch() throws Exception {
        // Initialize the database
        memeRepository.save(meme);

        int databaseSizeBeforeUpdate = memeRepository.findAll().size();

        // Update the meme using partial update
        Meme partialUpdatedMeme = new Meme();
        partialUpdatedMeme.setId(meme.getId());

        partialUpdatedMeme
            .slot(UPDATED_SLOT)
            .title(UPDATED_TITLE)
            .tag(UPDATED_TAG)
            .isDraft(UPDATED_IS_DRAFT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restMemeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeme.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMeme))
            )
            .andExpect(status().isOk());

        // Validate the Meme in the database
        List<Meme> memeList = memeRepository.findAll();
        assertThat(memeList).hasSize(databaseSizeBeforeUpdate);
        Meme testMeme = memeList.get(memeList.size() - 1);
        assertThat(testMeme.getSlot()).isEqualTo(UPDATED_SLOT);
        assertThat(testMeme.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMeme.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testMeme.getIsDraft()).isEqualTo(UPDATED_IS_DRAFT);
        assertThat(testMeme.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testMeme.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void patchNonExistingMeme() throws Exception {
        int databaseSizeBeforeUpdate = memeRepository.findAll().size();
        meme.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, meme.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(meme))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meme in the database
        List<Meme> memeList = memeRepository.findAll();
        assertThat(memeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMeme() throws Exception {
        int databaseSizeBeforeUpdate = memeRepository.findAll().size();
        meme.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(meme))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meme in the database
        List<Meme> memeList = memeRepository.findAll();
        assertThat(memeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMeme() throws Exception {
        int databaseSizeBeforeUpdate = memeRepository.findAll().size();
        meme.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(meme)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Meme in the database
        List<Meme> memeList = memeRepository.findAll();
        assertThat(memeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMeme() throws Exception {
        // Initialize the database
        memeRepository.save(meme);

        int databaseSizeBeforeDelete = memeRepository.findAll().size();

        // Delete the meme
        restMemeMockMvc
            .perform(delete(ENTITY_API_URL_ID, meme.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Meme> memeList = memeRepository.findAll();
        assertThat(memeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
