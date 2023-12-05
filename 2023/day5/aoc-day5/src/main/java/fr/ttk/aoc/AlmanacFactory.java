package fr.ttk.aoc;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fr.ttk.aoc.SimpleMap.SimpleMapBuilder;

public final class AlmanacFactory {

    private AlmanacFactory()
    {
        // Nothing Here
    }

    public static AlmanacRangeTransformer identity(final AlmanacRange range)
    {
        return SimpleRangeTransformer.builder().inputRange(range).shift(0).build();
    }

    public static AlmanacRangeTransformer createRangeTransformer(final Long destinationStart, final Long sourceStart, final Long rangeLength)
    {
        final long start = sourceStart;
        final long end = sourceStart + rangeLength;
        final long shift = destinationStart - sourceStart;

        final AlmanacRange range = SimpleRange.builder().start(start).end(end).build();

        return SimpleRangeTransformer.builder().inputRange(range).shift(shift).build();
    }

    public static AlmanacMap createMapNoGap(final Collection<AlmanacRangeTransformer> transformers)
    {   
        final Comparator<AlmanacRange> rangeComparator = Comparator.comparing(AlmanacRange::getStart);

        final List<AlmanacRangeTransformer> orderedTransformers = transformers.stream()
            .sorted(Comparator.comparing(AlmanacRangeTransformer::getInput, rangeComparator))
            .collect(Collectors.toList());

        final SimpleMapBuilder builder = SimpleMap.builder();


        final AlmanacRangeTransformer first = orderedTransformers.get(0);

        if (first.getInput().getStart() > 0)
        {
            final AlmanacRange gap = SimpleRange.builder().start(0).end(first.getInput().getStart()).build();
            builder.transformer(identity(gap));
        }

        builder.transformer(first); // add First

        for (int i = 1; i < orderedTransformers.size(); i++) {

            final AlmanacRangeTransformer before = orderedTransformers.get(i-1);
            final AlmanacRangeTransformer after = orderedTransformers.get(i);

            final AlmanacRange gap = SimpleRange.builder()
                .start(before.getInput().getEnd())
                .end(after.getInput().getStart()).build();

            if (gap.getSize() >= 1)
            {
                builder.transformer(identity(gap));
            }
            
            builder.transformer(after);
        }

        final AlmanacRangeTransformer last = orderedTransformers.get(orderedTransformers.size() - 1);

        final AlmanacRange lastGap = SimpleRange.builder()
            .start(last.getInput().getEnd())
            .end(Integer.MAX_VALUE).build();

        builder.transformer(identity(lastGap));

        return builder.build();
    }

}
