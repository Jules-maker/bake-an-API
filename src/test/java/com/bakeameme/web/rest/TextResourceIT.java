package com.bakeameme.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bakeameme.IntegrationTest;
import com.bakeameme.domain.Text;
import com.bakeameme.repository.TextRepository;
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
 * Integration tests for the {@link TextResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TextResourceIT {

    private static final Integer DEFAULT_COORDINATE_X = 1;
    private static final Integer UPDATED_COORDINATE_X = 2;

    private static final Integer DEFAULT_COORDINATE_Y = 1;
    private static final Integer UPDATED_COORDINATE_Y = 2;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/texts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TextRepository textRepository;

    @Autowired
    private MockMvc restTextMockMvc;

    private Text text;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Text createEntity() {
        Text text = new Text()
            .coordinateX(DEFAULT_COORDINATE_X)
            .coordinateY(DEFAULT_COORDINATE_Y)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return text;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Text createUpdatedEntity() {
        Text text = new Text()
            .coordinateX(UPDATED_COORDINATE_X)
            .coordinateY(UPDATED_COORDINATE_Y)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return text;
    }

    @BeforeEach
    public void initTest() {
        textRepository.deleteAll();
        text = createEntity();
    }

    @Test
    void createText() throws Exception {
        int databaseSizeBeforeCreate = textRepository.findAll().size();
        // Create the Text
        restTextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(text)))
            .andExpect(status().isCreated());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeCreate + 1);
        Text testText = textList.get(textList.size() - 1);
        assertThat(testText.getCoordinateX()).isEqualTo(DEFAULT_COORDINATE_X);
        assertThat(testText.getCoordinateY()).isEqualTo(DEFAULT_COORDINATE_Y);
        assertThat(testText.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testText.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    void createTextWithExistingId() throws Exception {
        // Create the Text with an existing ID
        text.setId("existing_id");

        int databaseSizeBeforeCreate = textRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(text)))
            .andExpect(status().isBadRequest());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTexts() throws Exception {
        // Initialize the database
        textRepository.save(text);

        // Get all the textList
        restTextMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(text.getId())))
            .andExpect(jsonPath("$.[*].coordinateX").value(hasItem(DEFAULT_COORDINATE_X)))
            .andExpect(jsonPath("$.[*].coordinateY").value(hasItem(DEFAULT_COORDINATE_Y)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    void getText() throws Exception {
        // Initialize the database
        textRepository.save(text);

        // Get the text
        restTextMockMvc
            .perform(get(ENTITY_API_URL_ID, text.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(text.getId()))
            .andExpect(jsonPath("$.coordinateX").value(DEFAULT_COORDINATE_X))
            .andExpect(jsonPath("$.coordinateY").value(DEFAULT_COORDINATE_Y))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    void getNonExistingText() throws Exception {
        // Get the text
        restTextMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingText() throws Exception {
        // Initialize the database
        textRepository.save(text);

        int databaseSizeBeforeUpdate = textRepository.findAll().size();

        // Update the text
        Text updatedText = textRepository.findById(text.getId()).get();
        updatedText
            .coordinateX(UPDATED_COORDINATE_X)
            .coordinateY(UPDATED_COORDINATE_Y)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedText.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedText))
            )
            .andExpect(status().isOk());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
        Text testText = textList.get(textList.size() - 1);
        assertThat(testText.getCoordinateX()).isEqualTo(UPDATED_COORDINATE_X);
        assertThat(testText.getCoordinateY()).isEqualTo(UPDATED_COORDINATE_Y);
        assertThat(testText.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testText.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void putNonExistingText() throws Exception {
        int databaseSizeBeforeUpdate = textRepository.findAll().size();
        text.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, text.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(text))
            )
            .andExpect(status().isBadRequest());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchText() throws Exception {
        int databaseSizeBeforeUpdate = textRepository.findAll().size();
        text.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(text))
            )
            .andExpect(status().isBadRequest());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamText() throws Exception {
        int databaseSizeBeforeUpdate = textRepository.findAll().size();
        text.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(text)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTextWithPatch() throws Exception {
        // Initialize the database
        textRepository.save(text);

        int databaseSizeBeforeUpdate = textRepository.findAll().size();

        // Update the text using partial update
        Text partialUpdatedText = new Text();
        partialUpdatedText.setId(text.getId());

        partialUpdatedText.coordinateX(UPDATED_COORDINATE_X).coordinateY(UPDATED_COORDINATE_Y).createdAt(UPDATED_CREATED_AT);

        restTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedText.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedText))
            )
            .andExpect(status().isOk());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
        Text testText = textList.get(textList.size() - 1);
        assertThat(testText.getCoordinateX()).isEqualTo(UPDATED_COORDINATE_X);
        assertThat(testText.getCoordinateY()).isEqualTo(UPDATED_COORDINATE_Y);
        assertThat(testText.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testText.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    void fullUpdateTextWithPatch() throws Exception {
        // Initialize the database
        textRepository.save(text);

        int databaseSizeBeforeUpdate = textRepository.findAll().size();

        // Update the text using partial update
        Text partialUpdatedText = new Text();
        partialUpdatedText.setId(text.getId());

        partialUpdatedText
            .coordinateX(UPDATED_COORDINATE_X)
            .coordinateY(UPDATED_COORDINATE_Y)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedText.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedText))
            )
            .andExpect(status().isOk());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
        Text testText = textList.get(textList.size() - 1);
        assertThat(testText.getCoordinateX()).isEqualTo(UPDATED_COORDINATE_X);
        assertThat(testText.getCoordinateY()).isEqualTo(UPDATED_COORDINATE_Y);
        assertThat(testText.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testText.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void patchNonExistingText() throws Exception {
        int databaseSizeBeforeUpdate = textRepository.findAll().size();
        text.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, text.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(text))
            )
            .andExpect(status().isBadRequest());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchText() throws Exception {
        int databaseSizeBeforeUpdate = textRepository.findAll().size();
        text.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(text))
            )
            .andExpect(status().isBadRequest());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamText() throws Exception {
        int databaseSizeBeforeUpdate = textRepository.findAll().size();
        text.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(text)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteText() throws Exception {
        // Initialize the database
        textRepository.save(text);

        int databaseSizeBeforeDelete = textRepository.findAll().size();

        // Delete the text
        restTextMockMvc
            .perform(delete(ENTITY_API_URL_ID, text.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
