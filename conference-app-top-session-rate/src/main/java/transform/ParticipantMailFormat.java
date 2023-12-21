package transform;

import model.Participants;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.join.CoGbkResult;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.TupleTag;
import util.EmailTransform;
import util.FormatEmail;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

public  class ParticipantMailFormat extends DoFn<KV<Integer, CoGbkResult>, Participants> {
    private  final TupleTag<String> mailTupleTag ;
    private  final TupleTag<Participants> participantTupleTag ;
    private  final EmailTransform emailTransform;
    private  final FormatEmail formatEmail;
    @Inject
    public ParticipantMailFormat(TupleTag<String> mailTupleTag, TupleTag<Participants> participantTupleTag, EmailTransform emailTransform, FormatEmail formatEmail) {
        this.mailTupleTag = mailTupleTag;
        this.participantTupleTag = participantTupleTag;
        this.emailTransform = emailTransform;
        this.formatEmail = formatEmail;
    }




    @ProcessElement
    public void processElement(ProcessContext c) {
        String mail = Objects.requireNonNull(c.element()).getValue().getOnly(mailTupleTag);
        Iterable<Participants> participants = Objects.requireNonNull(c.element()).getValue().getAll(participantTupleTag);
        List<Participants> participantsList = formatEmail.format(participants, emailTransform);
        participantsList.forEach(c::output);
    }
}