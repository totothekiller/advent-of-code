package fr.ttk.aoc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
public class SimpleMap implements AlmanacMap {

    @Singular private final List<AlmanacRangeTransformer> transformers;

    @Override
    public List<AlmanacRange> convert(final AlmanacRange input) {
        return transformers.stream()
            .map(t -> t.convert(input))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toUnmodifiableList());
    }

}
