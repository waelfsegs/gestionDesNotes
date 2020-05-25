package com.pfe.gestionnote.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CycleMapperTest {

    private CycleMapper cycleMapper;

    @BeforeEach
    public void setUp() {
        cycleMapper = new CycleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cycleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cycleMapper.fromId(null)).isNull();
    }
}
