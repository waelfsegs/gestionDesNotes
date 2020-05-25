package com.pfe.gestionnote.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class TypeEnseignementDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeEnseignementDTO.class);
        TypeEnseignementDTO typeEnseignementDTO1 = new TypeEnseignementDTO();
        typeEnseignementDTO1.setId(1L);
        TypeEnseignementDTO typeEnseignementDTO2 = new TypeEnseignementDTO();
        assertThat(typeEnseignementDTO1).isNotEqualTo(typeEnseignementDTO2);
        typeEnseignementDTO2.setId(typeEnseignementDTO1.getId());
        assertThat(typeEnseignementDTO1).isEqualTo(typeEnseignementDTO2);
        typeEnseignementDTO2.setId(2L);
        assertThat(typeEnseignementDTO1).isNotEqualTo(typeEnseignementDTO2);
        typeEnseignementDTO1.setId(null);
        assertThat(typeEnseignementDTO1).isNotEqualTo(typeEnseignementDTO2);
    }
}
