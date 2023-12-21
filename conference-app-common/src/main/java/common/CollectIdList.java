package common;

import org.apache.beam.sdk.transforms.Combine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CollectIdList extends Combine.CombineFn<String, List<String>, List<Integer>> implements Serializable {

    @Override
    public List<String> createAccumulator() {
        return new ArrayList<>();
    }

    @Override
    public List<String> addInput(List<String> mutableAccumulator, String input) {

        Objects.requireNonNull(mutableAccumulator).add(input);
        return mutableAccumulator;
    }

    @Override
    public List<String> mergeAccumulators(Iterable<List<String>> accumulators) {
        List<String> result = new ArrayList<>();
        for (List<String> list : accumulators) {
            result.addAll(list);
        }
        return result;
    }

    @Override
    public List<Integer> extractOutput(List<String> accumulator) {
        return Objects.requireNonNull(accumulator).stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
