package com.pfe.gestionnote.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class TypeEnseignementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeEnseignement.class);
        TypeEnseignement typeEnseignement1 = new TypeEnseignement();
        typeEnseignement1.setId(1L);
        TypeEnseignement typeEnseignement2 = new TypeEnseignement();
        typeEnseignement2.setId(typeEnseignement1.getId());
        assertThat(typeEnseignement1).isEqualTo(typeEnseignement2);
        typeEnseignement2.setId(2L);
        assertThat(typeEnseignement1).isNotEqualTo(typeEnseignement2);
        typeEnseignement1.setId(null);
        assertThat(typeEnseignement1).isNotEqualTo(typeEnseignement2);
    }
}
