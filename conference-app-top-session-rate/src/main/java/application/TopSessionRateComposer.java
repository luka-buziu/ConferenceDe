package application;


import common.CollectDataToList;
import common.KeyValueFn;
import common.ParticipantMail;
import common.db.read_db.ReadParticipantDB;
import model.Participants;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.Combine;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.join.CoGbkResult;
import org.apache.beam.sdk.transforms.join.CoGroupByKey;
import org.apache.beam.sdk.transforms.join.KeyedPCollectionTuple;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TupleTag;
import transform.MailMaker;
import transform.ParticipantMailFormat;
import transform.SessionIdKvFn;
import transform.SessionWithSpeakersFn;
import transform.db.ReadSessionDB;
import util.EmailTransform;
import util.FormatEmail;
import util.MailSender;

import javax.inject.Inject;

public class TopSessionRateComposer {

    private static final TupleTag<String> mailOutputTag = new TupleTag<>() {
    };
    private static final TupleTag<Participants> participantOutputTag = new TupleTag<>() {
    };
    private final ReadParticipantDB readParticipantDB;
    private final ReadSessionDB sessionDB;
    private final EmailTransform emailTransform;
    private final FormatEmail formatEmail;

    @Inject
    public TopSessionRateComposer(ReadParticipantDB readParticipantDB, ReadSessionDB sessionDB, EmailTransform emailTransform, FormatEmail formatEmail, MailSender mailSender) {
        this.readParticipantDB = readParticipantDB;
        this.sessionDB = sessionDB;
        this.emailTransform = emailTransform;
        this.formatEmail = formatEmail;
        ParticipantMail.sender= mailSender;
    }

    public void topSessionRateRunJob(Pipeline pipeline) {
        PCollection<KV<Integer, String>> email = pipeline.apply("Read Session from db", sessionDB)
                .apply("making a kv for session id", MapElements.via(new SessionIdKvFn()))
                .apply("Grouping by session id", GroupByKey.create())
                .apply("inserting all speakers into session", ParDo.of(new SessionWithSpeakersFn()))
                .apply("Getting a list of top sessions", Combine.globally(new CollectDataToList<>()))
                .apply("Making the mail for top session", ParDo.of(new MailMaker(emailTransform)))
                .apply("Making a kv for session", MapElements.via(new KeyValueFn<>()));

        PCollection<KV<Integer, Participants>> participant =
                pipeline.apply("Reading participants from db", readParticipantDB)
                        .apply("Making a kv for participant ", MapElements.via(new KeyValueFn<>()));

        PCollection<KV<Integer, CoGbkResult>> mailAndParticipant =
                KeyedPCollectionTuple.of(participantOutputTag, participant)
                .and(mailOutputTag, email)
                .apply(CoGroupByKey.create());

        mailAndParticipant.apply("Setting mail content to each participant",
                        ParDo.of(new ParticipantMailFormat(mailOutputTag, participantOutputTag, emailTransform, formatEmail)))
                .apply("Sending mail to each participant", ParDo.of(new ParticipantMail()));
    }
}
