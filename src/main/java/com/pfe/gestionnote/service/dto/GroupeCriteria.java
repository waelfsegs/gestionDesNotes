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
 * Criteria class for the {@link com.pfe.gestionnote.domain.Groupe} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.GroupeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /groupes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GroupeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomgroup;

    public GroupeCriteria() {
    }

    public GroupeCriteria(GroupeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomgroup = other.nomgroup == null ? null : other.nomgroup.copy();
    }

    @Override
    public GroupeCriteria copy() {
        return new GroupeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomgroup() {
        return nomgroup;
    }

    public void setNomgroup(StringFilter nomgroup) {
        this.nomgroup = nomgroup;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GroupeCriteria that = (GroupeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nomgroup, that.nomgroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nomgroup
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nomgroup != null ? "nomgroup=" + nomgroup + ", " : "") +
            "}";
    }

}
