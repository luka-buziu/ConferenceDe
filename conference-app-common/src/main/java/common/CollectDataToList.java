package common;

import org.apache.beam.sdk.transforms.Combine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CollectDataToList<T> extends Combine.CombineFn<T, List<T>, List<T>> implements Serializable {

    @Override
    public List<T> createAccumulator() {
        return new ArrayList<>();
    }

    @Override
    public List<T> addInput(List<T> mutableAccumulator, T input) {
        Objects.requireNonNull(mutableAccumulator).add(input);
        return mutableAccumulator;
    }

    @Override
    public List<T> mergeAccumulators(Iterable<List<T>> accumulators) {
        List<T> speakerMerge = createAccumulator();
        for (List<T> speakers : accumulators) {
            speakerMerge.addAll(speakers);
        }
        return speakerMerge;
    }

    @Override
    public List<T> extractOutput(List<T> accumulator) {
        return accumulator;
    }
}