package com.pfe.gestionnote.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class AffectationChefDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AffectationChefDTO.class);
        AffectationChefDTO affectationChefDTO1 = new AffectationChefDTO();
        affectationChefDTO1.setId(1L);
        AffectationChefDTO affectationChefDTO2 = new AffectationChefDTO();
        assertThat(affectationChefDTO1).isNotEqualTo(affectationChefDTO2);
        affectationChefDTO2.setId(affectationChefDTO1.getId());
        assertThat(affectationChefDTO1).isEqualTo(affectationChefDTO2);
        affectationChefDTO2.setId(2L);
        assertThat(affectationChefDTO1).isNotEqualTo(affectationChefDTO2);
        affectationChefDTO1.setId(null);
        assertThat(affectationChefDTO1).isNotEqualTo(affectationChefDTO2);
    }
}
