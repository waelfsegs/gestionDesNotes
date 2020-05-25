package com.pfe.gestionnote.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class ResultatDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultatDTO.class);
        ResultatDTO resultatDTO1 = new ResultatDTO();
        resultatDTO1.setId(1L);
        ResultatDTO resultatDTO2 = new ResultatDTO();
        assertThat(resultatDTO1).isNotEqualTo(resultatDTO2);
        resultatDTO2.setId(resultatDTO1.getId());
        assertThat(resultatDTO1).isEqualTo(resultatDTO2);
        resultatDTO2.setId(2L);
        assertThat(resultatDTO1).isNotEqualTo(resultatDTO2);
        resultatDTO1.setId(null);
        assertThat(resultatDTO1).isNotEqualTo(resultatDTO2);
    }
}
