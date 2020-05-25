package com.pfe.gestionnote.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ResultatMapperTest {

    private ResultatMapper resultatMapper;

    @BeforeEach
    public void setUp() {
        resultatMapper = new ResultatMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(resultatMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(resultatMapper.fromId(null)).isNull();
    }
}
