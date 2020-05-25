package com.pfe.gestionnote.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class RegimeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegimeDTO.class);
        RegimeDTO regimeDTO1 = new RegimeDTO();
        regimeDTO1.setId(1L);
        RegimeDTO regimeDTO2 = new RegimeDTO();
        assertThat(regimeDTO1).isNotEqualTo(regimeDTO2);
        regimeDTO2.setId(regimeDTO1.getId());
        assertThat(regimeDTO1).isEqualTo(regimeDTO2);
        regimeDTO2.setId(2L);
        assertThat(regimeDTO1).isNotEqualTo(regimeDTO2);
        regimeDTO1.setId(null);
        assertThat(regimeDTO1).isNotEqualTo(regimeDTO2);
    }
}
