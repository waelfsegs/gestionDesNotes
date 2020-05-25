package com.pfe.gestionnote.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class CycleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CycleDTO.class);
        CycleDTO cycleDTO1 = new CycleDTO();
        cycleDTO1.setId(1L);
        CycleDTO cycleDTO2 = new CycleDTO();
        assertThat(cycleDTO1).isNotEqualTo(cycleDTO2);
        cycleDTO2.setId(cycleDTO1.getId());
        assertThat(cycleDTO1).isEqualTo(cycleDTO2);
        cycleDTO2.setId(2L);
        assertThat(cycleDTO1).isNotEqualTo(cycleDTO2);
        cycleDTO1.setId(null);
        assertThat(cycleDTO1).isNotEqualTo(cycleDTO2);
    }
}
