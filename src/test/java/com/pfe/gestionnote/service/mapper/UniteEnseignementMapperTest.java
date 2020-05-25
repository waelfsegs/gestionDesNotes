package com.pfe.gestionnote.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UniteEnseignementMapperTest {

    private UniteEnseignementMapper uniteEnseignementMapper;

    @BeforeEach
    public void setUp() {
        uniteEnseignementMapper = new UniteEnseignementMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(uniteEnseignementMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(uniteEnseignementMapper.fromId(null)).isNull();
    }
}
