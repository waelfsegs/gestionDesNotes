package com.pfe.gestionnote.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class CorrigeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Corrige.class);
        Corrige corrige1 = new Corrige();
        corrige1.setId(1L);
        Corrige corrige2 = new Corrige();
        corrige2.setId(corrige1.getId());
        assertThat(corrige1).isEqualTo(corrige2);
        corrige2.setId(2L);
        assertThat(corrige1).isNotEqualTo(corrige2);
        corrige1.setId(null);
        assertThat(corrige1).isNotEqualTo(corrige2);
    }
}
