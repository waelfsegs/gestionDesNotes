package com.pfe.gestionnote.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class SemstreDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SemstreDTO.class);
        SemstreDTO semstreDTO1 = new SemstreDTO();
        semstreDTO1.setId(1L);
        SemstreDTO semstreDTO2 = new SemstreDTO();
        assertThat(semstreDTO1).isNotEqualTo(semstreDTO2);
        semstreDTO2.setId(semstreDTO1.getId());
        assertThat(semstreDTO1).isEqualTo(semstreDTO2);
        semstreDTO2.setId(2L);
        assertThat(semstreDTO1).isNotEqualTo(semstreDTO2);
        semstreDTO1.setId(null);
        assertThat(semstreDTO1).isNotEqualTo(semstreDTO2);
    }
}
