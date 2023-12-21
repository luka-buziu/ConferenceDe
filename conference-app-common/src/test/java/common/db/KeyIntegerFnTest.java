package common.db;

import common.KeyIntegerFn;
import org.apache.beam.sdk.values.KV;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;

public class KeyIntegerFnTest implements Serializable {

    @Test
    public void apply() {
        KeyIntegerFn<String> keyIntegerFn = new KeyIntegerFn<>();
        String testData = "luka";
        KV<String, Integer> keyInteger = keyIntegerFn.apply(testData);
        Assert.assertTrue(keyInteger.getKey().equals("luka") && keyInteger.getValue() == 1);
    }
}
