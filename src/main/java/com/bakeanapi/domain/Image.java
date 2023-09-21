package com.bakeanapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Image.
 */
@Document(collection = "image")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("position")
    private Integer position;

    @NotNull
    @Field("url")
    private String url;

    @Field("created_at")
    private Instant createdAt;

    @Field("updated_at")
    private Instant updatedAt;

    @DBRef
    @Field("text")
    @JsonIgnoreProperties(value = { "imageId" }, allowSetters = true)
    private Set<Text> texts = new HashSet<>();

    @DBRef
    @Field("memeId")
    @JsonIgnoreProperties(value = { "images", "dispositions" }, allowSetters = true)
    private Meme memeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Image id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPosition() {
        return this.position;
    }

    public Image position(Integer position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getUrl() {
        return this.url;
    }

    public Image url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Image createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Image updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Text> getTexts() {
        return this.texts;
    }

    public void setTexts(Set<Text> texts) {
        if (this.texts != null) {
            this.texts.forEach(i -> i.setImageId(null));
        }
        if (texts != null) {
            texts.forEach(i -> i.setImageId(this));
        }
        this.texts = texts;
    }

    public Image texts(Set<Text> texts) {
        this.setTexts(texts);
        return this;
    }

    public Image addText(Text text) {
        this.texts.add(text);
        text.setImageId(this);
        return this;
    }

    public Image removeText(Text text) {
        this.texts.remove(text);
        text.setImageId(null);
        return this;
    }

    public Meme getMemeId() {
        return this.memeId;
    }

    public void setMemeId(Meme meme) {
        this.memeId = meme;
    }

    public Image memeId(Meme meme) {
        this.setMemeId(meme);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }
        return id != null && id.equals(((Image) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", position=" + getPosition() +
            ", url='" + getUrl() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
