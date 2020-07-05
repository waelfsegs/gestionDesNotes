package com.pfe.gestionnote.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.pfe.gestionnote.domain.Enveloppe} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.EnveloppeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /enveloppes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnveloppeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nameenv;

    private LongFilter maiereId;

    public EnveloppeCriteria() {
    }

    public EnveloppeCriteria(EnveloppeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nameenv = other.nameenv == null ? null : other.nameenv.copy();
        this.maiereId = other.maiereId == null ? null : other.maiereId.copy();
    }

    @Override
    public EnveloppeCriteria copy() {
        return new EnveloppeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNameenv() {
        return nameenv;
    }

    public void setNameenv(StringFilter nameenv) {
        this.nameenv = nameenv;
    }

    public LongFilter getMaiereId() {
        return maiereId;
    }

    public void setMaiereId(LongFilter maiereId) {
        this.maiereId = maiereId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EnveloppeCriteria that = (EnveloppeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nameenv, that.nameenv) &&
            Objects.equals(maiereId, that.maiereId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nameenv,
        maiereId
        );
    }

    @Override
    public String toString() {
        return "EnveloppeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nameenv != null ? "nameenv=" + nameenv + ", " : "") +
                (maiereId != null ? "maiereId=" + maiereId + ", " : "") +
            "}";
    }

}
