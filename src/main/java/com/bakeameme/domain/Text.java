package com.bakeameme.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Text.
 */
@Document(collection = "text")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Text implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("coordinate_x")
    private Integer coordinateX;

    @Field("coordinate_y")
    private Integer coordinateY;

    @Field("created_at")
    private Instant createdAt;

    @Field("updated_at")
    private Instant updatedAt;

    @DBRef
    @Field("imageId")
    @JsonIgnoreProperties(value = { "texts", "memeId" }, allowSetters = true)
    private Image imageId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Text id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCoordinateX() {
        return this.coordinateX;
    }

    public Text coordinateX(Integer coordinateX) {
        this.setCoordinateX(coordinateX);
        return this;
    }

    public void setCoordinateX(Integer coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Integer getCoordinateY() {
        return this.coordinateY;
    }

    public Text coordinateY(Integer coordinateY) {
        this.setCoordinateY(coordinateY);
        return this;
    }

    public void setCoordinateY(Integer coordinateY) {
        this.coordinateY = coordinateY;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Text createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Text updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Image getImageId() {
        return this.imageId;
    }

    public void setImageId(Image image) {
        this.imageId = image;
    }

    public Text imageId(Image image) {
        this.setImageId(image);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Text)) {
            return false;
        }
        return id != null && id.equals(((Text) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Text{" +
            "id=" + getId() +
            ", coordinateX=" + getCoordinateX() +
            ", coordinateY=" + getCoordinateY() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
