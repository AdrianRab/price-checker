package com.pricechecker.tui.pricechecker;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HealthCheck {

    @Autowired
    private EmailSender emailSender;

    @PostConstruct
    private void startupConfirmation() {
        String eweMail = "ewe89@o2.pl";
        String adiMail = "adi8912@poczta.fm";
        String title = "Monitoring TUI Price";
        String text = "Aplikacja została poprawnie uruchomiona z dniem: <br>" + DataPuller.getCurrentDate() + "<br> Co godzinę będzie sprawdzać cenę hotelu Alegria. <br>" +
                "W przypadku zmiany ceny (na wyższą, bądź niższą) wyśle notyfikację. <br>" +
                "Enjoy!!!";
        try {
            emailSender.sendMail(eweMail, title, text, true);
            emailSender.sendMail(adiMail, title, text, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
