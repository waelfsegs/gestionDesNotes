package com.pfe.gestionnote.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CorrigeMapperTest {

    private CorrigeMapper corrigeMapper;

    @BeforeEach
    public void setUp() {
        corrigeMapper = new CorrigeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(corrigeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(corrigeMapper.fromId(null)).isNull();
    }
}
