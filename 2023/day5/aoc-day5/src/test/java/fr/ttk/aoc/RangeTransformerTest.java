package fr.ttk.aoc;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RangeTransformerTest {

    @Test
    public void testRangeTransformer()
    {
        AlmanacRange r1 = SimpleRange.builder().start(0).end(10).build();
        SimpleRangeTransformer rt1 = SimpleRangeTransformer.builder().inputRange(r1).shift(10).build();

        Assertions.assertThat(rt1.getOutput()).isEqualTo(SimpleRange.builder().start(10).end(20).build());
        Assertions.assertThat(rt1.convert(r1)).hasValue(SimpleRange.builder().start(10).end(20).build());
        
        AlmanacRange r2 = SimpleRange.builder().start(4).end(7).build();
        Assertions.assertThat(rt1.convert(r2)).hasValue(SimpleRange.builder().start(14).end(17).build());

        AlmanacRange r3 = SimpleRange.builder().start(5).end(15).build();
        Assertions.assertThat(rt1.convert(r3)).hasValue(SimpleRange.builder().start(15).end(20).build());

        AlmanacRange r4 = SimpleRange.builder().start(-10).end(5).build();
        Assertions.assertThat(rt1.convert(r4)).hasValue(SimpleRange.builder().start(10).end(15).build());
    }
}
