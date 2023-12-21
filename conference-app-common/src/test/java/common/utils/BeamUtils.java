package common.utils;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

public class BeamUtils {

    public static  Pipeline createPipeline(){
        return Pipeline.create(PipelineOptionsFactory.create());
    }
}
