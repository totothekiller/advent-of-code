package fr.ttk.aoc;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RangeTest {

    @Test
    public void testSize()
    {
        AlmanacRange r1 = SimpleRange.builder().start(0).end(10).build();
        Assertions.assertThat(r1.getSize()).isEqualTo(10);
    }

    @Test
    public void testShift()
    {
        AlmanacRange r1 = SimpleRange.builder().start(0).end(10).build();
        AlmanacRange r2 = SimpleRange.builder().start(10).end(20).build();

        Assertions.assertThat(r1.shiftBy(10)).isEqualTo(r2);
    }

    @Test
    public void testIntersect()
    {  
        AlmanacRange r1 = SimpleRange.builder().start(0).end(10).build();
        AlmanacRange r2 = SimpleRange.builder().start(10).end(20).build();
        AlmanacRange r3 = SimpleRange.builder().start(-10).end(0).build();
        AlmanacRange r4 = SimpleRange.builder().start(5).end(15).build();
        AlmanacRange r5 = SimpleRange.builder().start(-5).end(5).build();
        AlmanacRange r6 = SimpleRange.builder().start(4).end(6).build();

        Assertions.assertThat(r1.intersect(r2)).isEmpty();
        Assertions.assertThat(r1.intersect(r1)).hasValue(r1);
        Assertions.assertThat(r1.intersect(r3)).isEmpty();
        Assertions.assertThat(r1.intersect(r4)).hasValue(SimpleRange.builder().start(5).end(10).build());
        Assertions.assertThat(r1.intersect(r5)).hasValue(SimpleRange.builder().start(0).end(5).build());
        Assertions.assertThat(r1.intersect(r6)).hasValue(r6);
    }
}
