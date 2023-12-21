package application;


import injection.DaggerTopSessionRateComposerDag;
import injection.TopSessionRateComposerDag;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

public class TopSessionRateApp {
    public static void main(String[] args) {
//
        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options);
        TopSessionRateComposerDag topSessionRateComposer= DaggerTopSessionRateComposerDag.create();
       topSessionRateComposer.composer().topSessionRateRunJob(pipeline);
       pipeline.run();
    }
}
