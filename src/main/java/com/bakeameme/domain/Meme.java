package com.bakeameme.domain;

import com.bakeameme.domain.enumeration.Tags;
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
 * A Meme.
 */
@Document(collection = "meme")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Meme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("slot")
    private Integer slot;

    @Field("title")
    private String title;

    @Field("tag")
    private Tags tag;

    @Field("is_draft")
    private Boolean isDraft;

    @Field("created_at")
    private Instant createdAt;

    @Field("updated_at")
    private Instant updatedAt;

    @DBRef
    @Field("image")
    @JsonIgnoreProperties(value = { "texts", "memeId" }, allowSetters = true)
    private Set<Image> images = new HashSet<>();

    @DBRef
    @Field("disposition")
    @JsonIgnoreProperties(value = { "memeId" }, allowSetters = true)
    private Set<Disposition> dispositions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Meme id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSlot() {
        return this.slot;
    }

    public Meme slot(Integer slot) {
        this.setSlot(slot);
        return this;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public String getTitle() {
        return this.title;
    }

    public Meme title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Tags getTag() {
        return this.tag;
    }

    public Meme tag(Tags tag) {
        this.setTag(tag);
        return this;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
    }

    public Boolean getIsDraft() {
        return this.isDraft;
    }

    public Meme isDraft(Boolean isDraft) {
        this.setIsDraft(isDraft);
        return this;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Meme createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Meme updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Image> getImages() {
        return this.images;
    }

    public void setImages(Set<Image> images) {
        if (this.images != null) {
            this.images.forEach(i -> i.setMemeId(null));
        }
        if (images != null) {
            images.forEach(i -> i.setMemeId(this));
        }
        this.images = images;
    }

    public Meme images(Set<Image> images) {
        this.setImages(images);
        return this;
    }

    public Meme addImage(Image image) {
        this.images.add(image);
        image.setMemeId(this);
        return this;
    }

    public Meme removeImage(Image image) {
        this.images.remove(image);
        image.setMemeId(null);
        return this;
    }

    public Set<Disposition> getDispositions() {
        return this.dispositions;
    }

    public void setDispositions(Set<Disposition> dispositions) {
        if (this.dispositions != null) {
            this.dispositions.forEach(i -> i.setMemeId(null));
        }
        if (dispositions != null) {
            dispositions.forEach(i -> i.setMemeId(this));
        }
        this.dispositions = dispositions;
    }

    public Meme dispositions(Set<Disposition> dispositions) {
        this.setDispositions(dispositions);
        return this;
    }

    public Meme addDisposition(Disposition disposition) {
        this.dispositions.add(disposition);
        disposition.setMemeId(this);
        return this;
    }

    public Meme removeDisposition(Disposition disposition) {
        this.dispositions.remove(disposition);
        disposition.setMemeId(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meme)) {
            return false;
        }
        return id != null && id.equals(((Meme) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Meme{" +
            "id=" + getId() +
            ", slot=" + getSlot() +
            ", title='" + getTitle() + "'" +
            ", tag='" + getTag() + "'" +
            ", isDraft='" + getIsDraft() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
