package com.pfe.gestionnote.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DepartementMapperTest {

    private DepartementMapper departementMapper;

    @BeforeEach
    public void setUp() {
        departementMapper = new DepartementMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(departementMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(departementMapper.fromId(null)).isNull();
    }
}
