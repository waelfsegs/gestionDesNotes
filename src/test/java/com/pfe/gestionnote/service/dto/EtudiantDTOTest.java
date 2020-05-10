package com.pfe.gestionnote.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class EtudiantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtudiantDTO.class);
        EtudiantDTO etudiantDTO1 = new EtudiantDTO();
        etudiantDTO1.setId(1L);
        EtudiantDTO etudiantDTO2 = new EtudiantDTO();
        assertThat(etudiantDTO1).isNotEqualTo(etudiantDTO2);
        etudiantDTO2.setId(etudiantDTO1.getId());
        assertThat(etudiantDTO1).isEqualTo(etudiantDTO2);
        etudiantDTO2.setId(2L);
        assertThat(etudiantDTO1).isNotEqualTo(etudiantDTO2);
        etudiantDTO1.setId(null);
        assertThat(etudiantDTO1).isNotEqualTo(etudiantDTO2);
    }
}
