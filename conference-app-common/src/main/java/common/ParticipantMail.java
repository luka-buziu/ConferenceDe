package common;

import model.Participants;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.MailSender;

import java.io.Serializable;

public class ParticipantMail extends DoFn<Participants, Void> implements Serializable {
    private Logger logger=  LoggerFactory.getLogger(ParticipantMail.class);
    public static MailSender sender;
//
//    private void readObject(java.io.ObjectInputStream in)
//            throws IOException, ClassNotFoundException {
//        System.out.println("readObject");
//    }
//    private void writeObject(java.io.ObjectOutputStream out)
//            throws IOException{
//        System.out.println("writeObject");
//    }

    @ProcessElement
    public void processElement(ProcessContext c) {
        Participants participants = c.element();
        assert participants != null;
        sender.sendMail(participants.getEmail(),
                "Conference App", participants.getMailContent());
        System.out.println("success");
    }


}