package com.pfe.gestionnote.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeEnseignementMapperTest {

    private TypeEnseignementMapper typeEnseignementMapper;

    @BeforeEach
    public void setUp() {
        typeEnseignementMapper = new TypeEnseignementMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(typeEnseignementMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(typeEnseignementMapper.fromId(null)).isNull();
    }
}
