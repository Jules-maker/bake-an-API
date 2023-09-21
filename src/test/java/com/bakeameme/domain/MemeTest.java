package com.bakeameme.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bakeameme.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meme.class);
        Meme meme1 = new Meme();
        meme1.setId("id1");
        Meme meme2 = new Meme();
        meme2.setId(meme1.getId());
        assertThat(meme1).isEqualTo(meme2);
        meme2.setId("id2");
        assertThat(meme1).isNotEqualTo(meme2);
        meme1.setId(null);
        assertThat(meme1).isNotEqualTo(meme2);
    }
}
