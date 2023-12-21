package injection;

import common.db.JdbcConnector;
import dagger.Module;
import dagger.Provides;
import util.EmailTransform;
import util.FormatEmail;
import util.MailSender;

@Module
public abstract class MailModule {
    @Provides
    static EmailTransform emailTransform() {
        return new EmailTransform();
    }

    @Provides
    static FormatEmail formatEmail() {
        return new FormatEmail();
    }

    @Provides

    static MailSender mailSender() {
        return new MailSender();
    }

    @Provides
    static JdbcConnector jdbcConnector() {
        return new JdbcConnector();
    }
}
