package com.pricechecker.tui.pricechecker;

import com.pricechecker.tui.pricechecker.roomdetails.RoomDetails;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSender {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String to, String subject, String text, boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        log.info("Sending message to {}", to);
        javaMailSender.send(mimeMessage);
    }

    public void prepareAndSendNotificationForHigherPrice(RoomDetails roomDetails, Integer INITIAL_PRICE) throws MessagingException {
        String eweMail = roomDetails.getEmails().size() == 2 ? roomDetails.getEmails().get(0) : "ewe89@o2.pl";
        String adiMail = roomDetails.getEmails().size() == 2 ? roomDetails.getEmails().get(1) : "adi8912@poczta.fm";
        String title = "Wycieczka TUI - cena wzrosła!";
        String text = "Cena wycieczki się zmieniła z " + INITIAL_PRICE + " na " + roomDetails.getPrice() + ". <br> Czyli wzrosła. Słabo.";
        log.info("Price is higher");
        sendMail(eweMail, title, text, true);
        sendMail(adiMail, title, text, true);
    }

    public void prepareAndSendNotificationForLowerPrice(RoomDetails roomDetails, Integer INITIAL_PRICE) throws MessagingException {
        String eweMail = roomDetails.getEmails().size() == 2 ? roomDetails.getEmails().get(0) : "ewe89@o2.pl";
        String adiMail = roomDetails.getEmails().size() == 2 ? roomDetails.getEmails().get(1) : "adi8912@poczta.fm";
        String title = "Wycieczka TUI - cena spadła!";
        String urlToOffer = "https://www.tui.pl/wypoczynek/turcja/riwiera-turecka/side-alegria-hotel-spa-ayt42014/OfferCodeWS/WROAYT20200606043020200606202006201640L14AYT42014DZX2AA02";
        String text = "Cena wycieczki się zmieniła z " + INITIAL_PRICE + " na " + roomDetails.getPrice() + ". <br> Wejdź na <a href=" + urlToOffer + ">Alegria Hotel TUI</a>, zrób screen i wyślij do TUI.";
        log.info("Price is lower");
        sendMail(eweMail, title, text, true);
        sendMail(adiMail, title, text, true);
    }
}
