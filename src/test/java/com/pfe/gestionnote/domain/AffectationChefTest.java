package com.pfe.gestionnote.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class AffectationChefTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AffectationChef.class);
        AffectationChef affectationChef1 = new AffectationChef();
        affectationChef1.setId(1L);
        AffectationChef affectationChef2 = new AffectationChef();
        affectationChef2.setId(affectationChef1.getId());
        assertThat(affectationChef1).isEqualTo(affectationChef2);
        affectationChef2.setId(2L);
        assertThat(affectationChef1).isNotEqualTo(affectationChef2);
        affectationChef1.setId(null);
        assertThat(affectationChef1).isNotEqualTo(affectationChef2);
    }
}
