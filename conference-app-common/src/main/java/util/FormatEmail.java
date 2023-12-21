package util;

import model.Participants;
import org.apache.beam.repackaged.core.org.apache.commons.lang3.SerializationUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Singleton
public class FormatEmail implements Serializable {

    @Inject
    public FormatEmail() {
    }

    public List<Participants> format(Iterable<Participants> participantIterator, EmailTransform EMAIL_TRANSFORM) {
        List<Participants> collect = StreamSupport.stream(participantIterator.spliterator(), false)
                .map(participant1 -> {
                    Participants clone = SerializationUtils.clone(participant1);
                    clone.setMailContent(
                            EMAIL_TRANSFORM.mailTransform("[client]",
                                    participant1.getFirstName() + " " + participant1.getLastName(), "/emailData/testmail.txt"));
                    return clone;
                }).collect(Collectors.toList());
        collect.forEach(participants -> System.out.println(participants.getMailContent()));
        return collect;
    }
}
