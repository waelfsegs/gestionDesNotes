package com.pfe.gestionnote.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnseignementMapperTest {

    private EnseignementMapper enseignementMapper;

    @BeforeEach
    public void setUp() {
        enseignementMapper = new EnseignementMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(enseignementMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(enseignementMapper.fromId(null)).isNull();
    }
}
