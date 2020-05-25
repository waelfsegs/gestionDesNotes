package com.pfe.gestionnote.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SemstreMapperTest {

    private SemstreMapper semstreMapper;

    @BeforeEach
    public void setUp() {
        semstreMapper = new SemstreMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(semstreMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(semstreMapper.fromId(null)).isNull();
    }
}
