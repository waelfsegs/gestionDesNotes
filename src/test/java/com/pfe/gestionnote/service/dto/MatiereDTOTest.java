package com.pfe.gestionnote.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class MatiereDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatiereDTO.class);
        MatiereDTO matiereDTO1 = new MatiereDTO();
        matiereDTO1.setId(1L);
        MatiereDTO matiereDTO2 = new MatiereDTO();
        assertThat(matiereDTO1).isNotEqualTo(matiereDTO2);
        matiereDTO2.setId(matiereDTO1.getId());
        assertThat(matiereDTO1).isEqualTo(matiereDTO2);
        matiereDTO2.setId(2L);
        assertThat(matiereDTO1).isNotEqualTo(matiereDTO2);
        matiereDTO1.setId(null);
        assertThat(matiereDTO1).isNotEqualTo(matiereDTO2);
    }
}
