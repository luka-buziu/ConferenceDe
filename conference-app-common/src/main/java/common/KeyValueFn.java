package common;

import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;

import java.io.Serializable;

public class KeyValueFn<T> extends SimpleFunction<T, KV<Integer,T>> implements Serializable {
    @Override
    public KV<Integer, T> apply(T input) {
        return KV.of(1,input);
    }


}
