package com.pfe.gestionnote.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AffectationChefMapperTest {

    private AffectationChefMapper affectationChefMapper;

    @BeforeEach
    public void setUp() {
        affectationChefMapper = new AffectationChefMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(affectationChefMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(affectationChefMapper.fromId(null)).isNull();
    }
}
