package transform;

import model.Sessions;
import org.apache.beam.sdk.transforms.DoFn;
import util.EmailTransform;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public  class MailMaker extends DoFn<List<Sessions>, String> implements Serializable {
    private  final EmailTransform emailTransform;
    @Inject
    public MailMaker(EmailTransform emailTransform) {
        this.emailTransform = emailTransform;
    }

    @ProcessElement
    public void processElement(ProcessContext c) {
        List<Sessions> sessions = c.element();
        String sessionSpeaker = sessions.stream()
                .map(Sessions::toString).
                collect(Collectors.joining(" and "));
        String mail = emailTransform.mailTransform("[title of most viewed sessions]", sessionSpeaker,"/emailData/testmail.txt");
        c.output(mail);
    }
}