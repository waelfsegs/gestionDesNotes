package com.pfe.gestionnote.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EtudiantMapperTest {

    private EtudiantMapper etudiantMapper;

    @BeforeEach
    public void setUp() {
        etudiantMapper = new EtudiantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(etudiantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(etudiantMapper.fromId(null)).isNull();
    }
}
