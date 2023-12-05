package fr.ttk.aoc;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleRangeTransformer implements AlmanacRangeTransformer {

    private final AlmanacRange inputRange;

    private final long shift;

    @Override
    public Optional<AlmanacRange> convert(final AlmanacRange input) {
        return inputRange.intersect(input)
                .map(i -> i.shiftBy(shift));
    }

    @Override
    public AlmanacRange getInput() {
        return inputRange;
    }

    @Override
    public AlmanacRange getOutput() {
        return inputRange.shiftBy(shift);
    }

}
