package com.bakeanapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Disposition.
 */
@Document(collection = "disposition")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Disposition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("size_required")
    private Integer sizeRequired;

    @Field("coordinate_1_x")
    private Integer coordinate1X;

    @Field("coordinate_1_y")
    private Integer coordinate1Y;

    @Field("coordinate_2_x")
    private Integer coordinate2X;

    @Field("coordinate_2_y")
    private Integer coordinate2Y;

    @Field("coordinate_3_x")
    private Integer coordinate3X;

    @Field("coordinate_3_y")
    private Integer coordinate3Y;

    @Field("coordinate_4_x")
    private Integer coordinate4X;

    @Field("coordinate_4_y")
    private Integer coordinate4Y;

    @Field("created_at")
    private Instant createdAt;

    @Field("updated_at")
    private Instant updatedAt;

    @DBRef
    @Field("memeId")
    @JsonIgnoreProperties(value = { "images", "dispositions" }, allowSetters = true)
    private Meme memeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Disposition id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Disposition name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSizeRequired() {
        return this.sizeRequired;
    }

    public Disposition sizeRequired(Integer sizeRequired) {
        this.setSizeRequired(sizeRequired);
        return this;
    }

    public void setSizeRequired(Integer sizeRequired) {
        this.sizeRequired = sizeRequired;
    }

    public Integer getCoordinate1X() {
        return this.coordinate1X;
    }

    public Disposition coordinate1X(Integer coordinate1X) {
        this.setCoordinate1X(coordinate1X);
        return this;
    }

    public void setCoordinate1X(Integer coordinate1X) {
        this.coordinate1X = coordinate1X;
    }

    public Integer getCoordinate1Y() {
        return this.coordinate1Y;
    }

    public Disposition coordinate1Y(Integer coordinate1Y) {
        this.setCoordinate1Y(coordinate1Y);
        return this;
    }

    public void setCoordinate1Y(Integer coordinate1Y) {
        this.coordinate1Y = coordinate1Y;
    }

    public Integer getCoordinate2X() {
        return this.coordinate2X;
    }

    public Disposition coordinate2X(Integer coordinate2X) {
        this.setCoordinate2X(coordinate2X);
        return this;
    }

    public void setCoordinate2X(Integer coordinate2X) {
        this.coordinate2X = coordinate2X;
    }

    public Integer getCoordinate2Y() {
        return this.coordinate2Y;
    }

    public Disposition coordinate2Y(Integer coordinate2Y) {
        this.setCoordinate2Y(coordinate2Y);
        return this;
    }

    public void setCoordinate2Y(Integer coordinate2Y) {
        this.coordinate2Y = coordinate2Y;
    }

    public Integer getCoordinate3X() {
        return this.coordinate3X;
    }

    public Disposition coordinate3X(Integer coordinate3X) {
        this.setCoordinate3X(coordinate3X);
        return this;
    }

    public void setCoordinate3X(Integer coordinate3X) {
        this.coordinate3X = coordinate3X;
    }

    public Integer getCoordinate3Y() {
        return this.coordinate3Y;
    }

    public Disposition coordinate3Y(Integer coordinate3Y) {
        this.setCoordinate3Y(coordinate3Y);
        return this;
    }

    public void setCoordinate3Y(Integer coordinate3Y) {
        this.coordinate3Y = coordinate3Y;
    }

    public Integer getCoordinate4X() {
        return this.coordinate4X;
    }

    public Disposition coordinate4X(Integer coordinate4X) {
        this.setCoordinate4X(coordinate4X);
        return this;
    }

    public void setCoordinate4X(Integer coordinate4X) {
        this.coordinate4X = coordinate4X;
    }

    public Integer getCoordinate4Y() {
        return this.coordinate4Y;
    }

    public Disposition coordinate4Y(Integer coordinate4Y) {
        this.setCoordinate4Y(coordinate4Y);
        return this;
    }

    public void setCoordinate4Y(Integer coordinate4Y) {
        this.coordinate4Y = coordinate4Y;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Disposition createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Disposition updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Meme getMemeId() {
        return this.memeId;
    }

    public void setMemeId(Meme meme) {
        this.memeId = meme;
    }

    public Disposition memeId(Meme meme) {
        this.setMemeId(meme);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Disposition)) {
            return false;
        }
        return id != null && id.equals(((Disposition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Disposition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sizeRequired=" + getSizeRequired() +
            ", coordinate1X=" + getCoordinate1X() +
            ", coordinate1Y=" + getCoordinate1Y() +
            ", coordinate2X=" + getCoordinate2X() +
            ", coordinate2Y=" + getCoordinate2Y() +
            ", coordinate3X=" + getCoordinate3X() +
            ", coordinate3Y=" + getCoordinate3Y() +
            ", coordinate4X=" + getCoordinate4X() +
            ", coordinate4Y=" + getCoordinate4Y() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
