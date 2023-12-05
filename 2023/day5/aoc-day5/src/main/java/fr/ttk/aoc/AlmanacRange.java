package fr.ttk.aoc;

import java.util.Optional;

public interface AlmanacRange {

    long getStart();

    long getEnd();

    long getSize();

    AlmanacRange shiftBy(long by);

    Optional<AlmanacRange> intersect(AlmanacRange other);

}
