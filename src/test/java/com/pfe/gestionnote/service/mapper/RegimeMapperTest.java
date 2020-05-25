package com.pfe.gestionnote.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RegimeMapperTest {

    private RegimeMapper regimeMapper;

    @BeforeEach
    public void setUp() {
        regimeMapper = new RegimeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(regimeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(regimeMapper.fromId(null)).isNull();
    }
}
