package com.pfe.gestionnote.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class ClasseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classe.class);
        Classe classe1 = new Classe();
        classe1.setId(1L);
        Classe classe2 = new Classe();
        classe2.setId(classe1.getId());
        assertThat(classe1).isEqualTo(classe2);
        classe2.setId(2L);
        assertThat(classe1).isNotEqualTo(classe2);
        classe1.setId(null);
        assertThat(classe1).isNotEqualTo(classe2);
    }
}
