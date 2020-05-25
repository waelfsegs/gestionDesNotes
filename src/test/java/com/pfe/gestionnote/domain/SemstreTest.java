package com.pfe.gestionnote.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.gestionnote.web.rest.TestUtil;

public class SemstreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Semstre.class);
        Semstre semstre1 = new Semstre();
        semstre1.setId(1L);
        Semstre semstre2 = new Semstre();
        semstre2.setId(semstre1.getId());
        assertThat(semstre1).isEqualTo(semstre2);
        semstre2.setId(2L);
        assertThat(semstre1).isNotEqualTo(semstre2);
        semstre1.setId(null);
        assertThat(semstre1).isNotEqualTo(semstre2);
    }
}
