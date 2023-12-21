package application;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.Serializable;

public class SessionRatingJob implements Serializable {

    private static final ObjectMapper objectMapper =new ObjectMapper();
    private final RabbitMq rabbitMq;
    private String queueName="users";
    private final InsertInDB insertInDB;
    private final ReadAllFRDB readAllFRDB;


    @Inject
    public SessionRatingJob(RabbitMq rabbitMq, InsertInDB insertInDB, ReadAllFRDB readAllFRDB) {
        this.rabbitMq = rabbitMq;
        this.insertInDB = insertInDB;
        this.readAllFRDB = readAllFRDB;
    }

    public void composer(Pipeline pipeline) {

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