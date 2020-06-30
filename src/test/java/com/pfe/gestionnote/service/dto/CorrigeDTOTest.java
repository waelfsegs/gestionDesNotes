package com.pfe.gestionnote.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class CorrigeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrigeDTO.class);
        CorrigeDTO corrigeDTO1 = new CorrigeDTO();
        corrigeDTO1.setId(1L);
        CorrigeDTO corrigeDTO2 = new CorrigeDTO();
        assertThat(corrigeDTO1).isNotEqualTo(corrigeDTO2);
        corrigeDTO2.setId(corrigeDTO1.getId());
        assertThat(corrigeDTO1).isEqualTo(corrigeDTO2);
        corrigeDTO2.setId(2L);
        assertThat(corrigeDTO1).isNotEqualTo(corrigeDTO2);
        corrigeDTO1.setId(null);
        assertThat(corrigeDTO1).isNotEqualTo(corrigeDTO2);
    }
}
