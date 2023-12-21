package common.db;

import common.ParticipantMail;
import common.utils.BeamUtils;
import model.Participants;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.ParDo;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.AtLeast;
import util.MailSender;

import java.io.Serializable;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

public class ParticipantMailTest implements Serializable {


    @Test
    public void testMail(){
        MailSender mailMock=mock(MailSender.class,withSettings().verboseLogging());
//        when(mailMock.sendMail(any(String.class),any(String.class),any(String.class))).thenReturn(true);
        ParticipantMail.sender=mailMock;
        Pipeline pipeline = BeamUtils.createPipeline();
        Participants participants = new Participants("luka", "buziu", "lukabuziu1@gmail.com");
        participants.setMailContent("lola");
        Participants participants1 = new Participants("luka", "buziu", "lukabuz@gmail.com");
        participants1.setMailContent("faef");

        pipeline.apply(Create.of(participants,participants1))
                .apply(ParDo.of(new ParticipantMail()));
        pipeline.run().waitUntilFinish();

        Mockito.verify(mailMock, new AtLeast(2))
                .sendMail(anyString(),anyString(),anyString());


    }
}