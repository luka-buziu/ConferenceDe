package transform;


import model.Sessions;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;

import java.io.Serializable;

public  class SessionIdKvFn extends SimpleFunction<Sessions, KV<Integer, Sessions>> implements Serializable {
    @Override
    public KV<Integer, Sessions> apply(Sessions session) {
        return KV.of(session.getId(),session);
    }


}