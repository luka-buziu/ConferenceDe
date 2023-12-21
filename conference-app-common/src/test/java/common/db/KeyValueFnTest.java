package common.db;

import common.KeyValueFn;
import org.apache.beam.sdk.values.KV;
import org.junit.Test;
import org.junit.Assert;

import java.io.Serializable;

public class KeyValueFnTest implements Serializable {

    @Test
    public void apply() {
        KeyValueFn<String> keyValueFn = new KeyValueFn<>();
        KV<Integer, String> value = keyValueFn.apply("value");
        Assert.assertEquals(KV.of(1,"value"),value);
    }
}