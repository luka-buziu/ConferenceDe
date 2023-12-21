package common;

import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;

import java.io.Serializable;

public class KeyIntegerFn <T>extends SimpleFunction<T, KV<T,Integer>> implements Serializable {

    @Override
    public KV<T, Integer> apply(T input) {
        return KV.of(input,1);
    }
}