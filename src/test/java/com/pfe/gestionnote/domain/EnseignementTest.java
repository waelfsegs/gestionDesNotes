package com.pfe.gestionnote.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class EnseignementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enseignement.class);
        Enseignement enseignement1 = new Enseignement();
        enseignement1.setId(1L);
        Enseignement enseignement2 = new Enseignement();
        enseignement2.setId(enseignement1.getId());
        assertThat(enseignement1).isEqualTo(enseignement2);
        enseignement2.setId(2L);
        assertThat(enseignement1).isNotEqualTo(enseignement2);
        enseignement1.setId(null);
        assertThat(enseignement1).isNotEqualTo(enseignement2);
    }
}
