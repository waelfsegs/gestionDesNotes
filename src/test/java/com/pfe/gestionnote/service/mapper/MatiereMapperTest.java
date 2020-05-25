package com.pfe.gestionnote.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MatiereMapperTest {

    private MatiereMapper matiereMapper;

    @BeforeEach
    public void setUp() {
        matiereMapper = new MatiereMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(matiereMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(matiereMapper.fromId(null)).isNull();
    }
}
