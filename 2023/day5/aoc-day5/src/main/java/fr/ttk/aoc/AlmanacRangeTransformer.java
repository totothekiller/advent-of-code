package fr.ttk.aoc;

import java.util.Optional;

public interface AlmanacRangeTransformer {

    AlmanacRange getInput();

    AlmanacRange getOutput();

    Optional<AlmanacRange> convert(AlmanacRange input);
}
