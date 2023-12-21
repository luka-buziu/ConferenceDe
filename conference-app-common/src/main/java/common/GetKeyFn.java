package common;

import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;

import java.io.Serializable;

public class GetKeyFn <K,V> extends SimpleFunction<KV<K,V>,K> implements Serializable {

    @Override
    public K apply(KV<K, V> input) {
        return input.getKey();
    }

}
