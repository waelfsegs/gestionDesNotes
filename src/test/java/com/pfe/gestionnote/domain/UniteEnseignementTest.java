package com.pfe.gestionnote.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class UniteEnseignementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UniteEnseignement.class);
        UniteEnseignement uniteEnseignement1 = new UniteEnseignement();
        uniteEnseignement1.setId(1L);
        UniteEnseignement uniteEnseignement2 = new UniteEnseignement();
        uniteEnseignement2.setId(uniteEnseignement1.getId());
        assertThat(uniteEnseignement1).isEqualTo(uniteEnseignement2);
        uniteEnseignement2.setId(2L);
        assertThat(uniteEnseignement1).isNotEqualTo(uniteEnseignement2);
        uniteEnseignement1.setId(null);
        assertThat(uniteEnseignement1).isNotEqualTo(uniteEnseignement2);
    }
}
