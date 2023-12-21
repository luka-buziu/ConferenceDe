package application;

import common.CollectIdList;
import common.GetKeyFn;
import common.KeyIntegerFn;
import common.SessionRateKVFN;
import common.db.insert_db.InsertInDB;
import common.db.read_db.ReadAllFRDB;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.*;
import rabbitMq.RabbitMq;

import javax.inject.Inject;

public class SpeakerRatePipelineComposer {

    private final RabbitMq rabbitMq;
    private final ReadAllFRDB readAllFRDB;
    private final InsertInDB insertInDB;
    private final String queueName="speaker";

    @Inject
    public SpeakerRatePipelineComposer(RabbitMq rabbitMq, ReadAllFRDB readAllFRDB, InsertInDB insertInDB) {
        this.rabbitMq = rabbitMq;
        this.readAllFRDB = readAllFRDB;
        this.insertInDB = insertInDB;
    }

    public void speakerRatingRunJob(Pipeline pipeline){
        rabbitMq.readFromRabbitMq(pipeline,queueName)
                .apply(MapElements.via(new KeyIntegerFn<>()))
                .apply(Count.perKey())
                .apply(MapElements.via(new GetKeyFn<>()))
                .apply(Combine.globally(new CollectIdList()).withoutDefaults())
                .apply(readAllFRDB)
                .apply(ParDo.of(new SessionRateKVFN()))
                .apply(Mean.perKey())
                .apply(insertInDB);
    }
}
