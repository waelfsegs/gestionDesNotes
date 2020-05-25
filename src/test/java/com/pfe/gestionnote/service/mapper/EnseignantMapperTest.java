package com.pfe.gestionnote.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnseignantMapperTest {

    private EnseignantMapper enseignantMapper;

    @BeforeEach
    public void setUp() {
        enseignantMapper = new EnseignantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(enseignantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(enseignantMapper.fromId(null)).isNull();
    }
}
