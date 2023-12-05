package fr.ttk.aoc;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleRange implements AlmanacRange{

    private final long start;

    private final long end;

    @Override
    public long getStart() {
        return start;
    }

    @Override
    public long getEnd() {
        return end;
    }

    @Override
    public long getSize() {
        return end - start;
    }

    @Override
    public AlmanacRange shiftBy(final long by) {
        return SimpleRange.builder()
            .start(start + by)
            .end(end + by)
            .build();
    }

    @Override
    public Optional<AlmanacRange> intersect(final AlmanacRange other) {

        final long low = Math.max(start, other.getStart());
        final long high = Math.min(end, other.getEnd());

        if (high <= low) 
        {
            return Optional.empty();
        } else {
            return Optional.of(SimpleRange.builder().start(low).end(high).build());
        }
    }

}
