package util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Serializable;

@Singleton
public class MailSender implements Serializable {

    private Logger logger = LoggerFactory.getLogger(MailSender.class);

    @Inject
    public MailSender() {
    }

    public void sendMail(String participant, String subject, String content) {

        try {
            Email email = new SimpleEmail();
            email.setSubject(subject);
            email.addTo(participant);
            email.setFrom("lukabuziu42@gmail.com");
            email.setMsg(content);
            email.setSmtpPort(587);
            email.setHostName("smtp.gmail.com");
            email.setAuthenticator(new DefaultAuthenticator("lukabuziu42@gmail.com", "vufiijgnojpzuqpr"));
            email.setTLS(true);
            email.send();

        } catch (EmailException e) {
            logger.error("Error sending email" + e.getStackTrace());

        }
    }

}
