package fr.ttk.aoc;

import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RangeMapTest {

    @Test
    public void testSimpleMap() {

        AlmanacRange r1 = SimpleRange.builder().start(0).end(10).build();
        AlmanacRangeTransformer rt1 = SimpleRangeTransformer.builder().inputRange(r1).shift(10).build();

        AlmanacRange r2 = SimpleRange.builder().start(20).end(30).build();
        AlmanacRangeTransformer rt2 = SimpleRangeTransformer.builder().inputRange(r2).shift(20).build();

        AlmanacMap m1 = SimpleMap.builder().transformer(rt1).transformer(rt2).build();

        Assertions.assertThat(m1.convert(r1)).containsExactly(SimpleRange.builder().start(10).end(20).build());
        Assertions.assertThat(m1.convert(r2)).containsExactly(SimpleRange.builder().start(40).end(50).build());

        AlmanacRange r3 = SimpleRange.builder().start(0).end(30).build();
        Assertions.assertThat(m1.convert(r3)).containsExactly(SimpleRange.builder().start(10).end(20).build(), SimpleRange.builder().start(40).end(50).build());
    }

    @Test
    public void testMapNoGap()
    {
        AlmanacRange r1 = SimpleRange.builder().start(10).end(20).build();
        AlmanacRangeTransformer rt1 = SimpleRangeTransformer.builder().inputRange(r1).shift(10).build();

        AlmanacRange r2 = SimpleRange.builder().start(30).end(40).build();
        AlmanacRangeTransformer rt2 = SimpleRangeTransformer.builder().inputRange(r2).shift(20).build();

        AlmanacMap m1 = AlmanacFactory.createMapNoGap(Arrays.asList(rt2, rt1));

        // Full Range
        AlmanacRange r3 = SimpleRange.builder().start(0).end(50).build();
        Assertions.assertThat(m1.convert(r3)).containsExactly(
            SimpleRange.builder().start(0).end(10).build(), 
            SimpleRange.builder().start(20).end(30).build(), 
            SimpleRange.builder().start(20).end(30).build(),
            SimpleRange.builder().start(50).end(60).build(),
            SimpleRange.builder().start(40).end(50).build());
    }

}
