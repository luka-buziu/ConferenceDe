package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class Participants implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String mailContent;

    @Inject
    public Participants(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }
}
