package fr.ttk.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;

public class Day5 {

    @Test
    public void part1() throws IOException
    {
        final List<String> lines = Files.readAllLines(Path.of("src/test/resources/input.txt"));
        Assertions.assertThat(lines).isNotEmpty();

        final List<AlmanacRange> seeds = readSeeds(lines.get(0));
        Assertions.assertThat(seeds).isNotEmpty();

        final List<AlmanacMap> maps = readMaps(lines.subList(2, lines.size()));
        Assertions.assertThat(maps).hasSize(7);

        System.out.println("Part1");
        System.out.println(seeds.stream().map(s -> process(s, maps)).flatMap(List::stream).mapToLong(AlmanacRange::getStart).min().getAsLong());
    }

    @Test
    public void part2() throws IOException
    {
        final List<String> lines = Files.readAllLines(Path.of("src/test/resources/input.txt"));
        Assertions.assertThat(lines).isNotEmpty();

        final List<AlmanacRange> seeds = readSeeds2(lines.get(0));
        Assertions.assertThat(seeds).isNotEmpty();

        final List<AlmanacMap> maps = readMaps(lines.subList(2, lines.size()));
        Assertions.assertThat(maps).hasSize(7);

        System.out.println("Part2");
        System.out.println(seeds.stream().map(s -> process(s, maps)).flatMap(List::stream).mapToLong(AlmanacRange::getStart).min().getAsLong());
    }

    private AlmanacRangeTransformer createRangeTransformerFromLine(final String line)
    {
        final String[] parts = line.split(" ");
        return AlmanacFactory.createRangeTransformer(Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2]));
    }

    @Test
    public void testCreateRangeTransformerFromLine()
    {
        AlmanacRangeTransformer rt = createRangeTransformerFromLine("50 98 2");

        Assertions.assertThat(rt.getInput()).isEqualTo(SimpleRange.builder().start(98).end(100).build());
        Assertions.assertThat(rt.getOutput()).isEqualTo(SimpleRange.builder().start(50).end(52).build());
    }

    private List<AlmanacRange> readSeeds(final String line)
    {
        final String[] parts = line.split(" ");

        return Arrays.stream(parts)
            .skip(1)
            .mapToLong(Long::parseLong)
            .mapToObj(i -> SimpleRange.builder().start(i).end(i + 1).build())
            .collect(Collectors.toUnmodifiableList());
    }

    private List<AlmanacRange> readSeeds2(final String line)
    {
        final String[] parts = line.split(" ");

        List<AlmanacRange> ranges = new ArrayList<>();

        for (int i = 1; i < parts.length; i+=2) {

            ranges.add(SimpleRange.builder().start(Long.parseLong(parts[i])).end(Long.parseLong(parts[i]) + Long.parseLong(parts[i + 1])).build());
        }
        
        return Collections.unmodifiableList(ranges);
    }

    @Test
    public void testReadSeeds()
    {
        List<AlmanacRange> seeds = readSeeds("seeds: 79 14 55 13");

        Assertions.assertThat(seeds).hasSize(4);
    }

    private List<AlmanacMap> readMaps(final List<String> lines)
    {
        final List<AlmanacMap> maps = new ArrayList<>(7);

        List<AlmanacRangeTransformer> currentMap = null;

        for (String line : lines) {
            
            if (line.contains("map"))
            {
                if (currentMap != null)
                {
                    maps.add(AlmanacFactory.createMapNoGap(currentMap));
                }

                currentMap = new ArrayList<>();
            } else if (!Strings.isNullOrEmpty(line))
            {
                currentMap.add(createRangeTransformerFromLine(line));
            }
        }

        // Add Last
        if (currentMap != null)
        {
            maps.add(AlmanacFactory.createMapNoGap(currentMap));
        }

        return Collections.unmodifiableList(maps);
    }

    @Test
    public void readMapTest()
    {
        List<AlmanacMap> maps = readMaps(Arrays.asList("seed-to-soil map:", "50 98 2", "52 50 48", ""));

        Assertions.assertThat(maps).hasSize(1);
    }

    private List<AlmanacRange> process(final AlmanacRange seed, final List<AlmanacMap> maps)
    {
        List<AlmanacRange> inputs = Arrays.asList(seed);

        for (AlmanacMap map : maps) {
            
            List<AlmanacRange> outputs = new ArrayList<>();

            for (AlmanacRange input : inputs) {
                outputs.addAll(map.convert(input));
            }

            inputs = outputs;
        }

        return Collections.unmodifiableList(inputs);
    }

}
