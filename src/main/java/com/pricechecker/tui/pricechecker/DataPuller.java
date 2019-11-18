package com.pricechecker.tui.pricechecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPuller {
    public static final String ROOM_CODE = "DZX2";
    private int initialPrice = 7490;
    private EmailSender emailSender;

    @Autowired
    public DataPuller(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    RoomDetails checkPriceAndEnrichResponse(StringBuilder response) {
        return RoomDetailsParser.parse(response.toString(), checkPriceAndSendNotification(response.toString()));
    }

    StringBuilder connectAndPullData(String jsonInputString, URL tuiPrices) throws IOException {
        HttpURLConnection connection = getHttpURLConnection(tuiPrices);
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        StringBuilder result = new StringBuilder();

        System.out.println(connection.getResponseCode());
        System.out.println(connection.getResponseMessage());

        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = in.readLine()) != null)
                result.append(responseLine);
        }
        System.out.println(result.toString());
        return result;
    }

    private HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        return connection;
    }

    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    private String checkPriceAndSendNotification(String response) {
        try {
            int newPrice = Integer.valueOf(response.substring(response.indexOf(ROOM_CODE) + 14, response.indexOf(ROOM_CODE) + 18));
            return comparePrice(newPrice);
        } catch (NumberFormatException ex) {
            throw new IllegalStateException("Wrong number format, check response and parsing", ex);
        } catch (MessagingException ex) {
            throw new IllegalStateException("Something is wrong with message you try to send", ex);
        }
    }

    private String comparePrice(int newPrice) throws MessagingException {
        if (initialPrice > newPrice) {
            prepareAndSendNotificationForLowerPrice(newPrice);
            initialPrice = newPrice;
            System.out.println(initialPrice);
            return "Cena jest niższa!!! Jedyne " + newPrice + " zł";
        } else if (initialPrice < newPrice) {
            prepareAndSendNotificationForHigherPrice(newPrice);
            System.out.println(newPrice);
            return "Cena jest wyższa niż podczas zakupu: " + newPrice + " zł";
        }
        return "Cena się nie zmieniła: " + initialPrice + " zł";
    }

    private void prepareAndSendNotificationForHigherPrice(int newPrice) throws MessagingException {
        String eweMail = "ewe89@o2.pl";
        String adiMail = "adi8912@poczta.fm";
        String title = "Wycieczka TUI - zmiana ceny!";
        String text = "Cena wycieczki się zmieniła z " + initialPrice + " na " + newPrice + ". <br> Czyli wzrosła. Słabo.";
        emailSender.sendMail(eweMail, title, text, true);
        emailSender.sendMail(adiMail, title, text, true);
    }

    private void prepareAndSendNotificationForLowerPrice(int newPrice) throws MessagingException {
        String eweMail = "ewe89@o2.pl";
        String adiMail = "adi8912@poczta.fm";
        String title = "Wycieczka TUI - zmiana ceny!";
        String urlToOffer = "https://www.tui.pl/wypoczynek/turcja/riwiera-turecka/side-alegria-hotel-spa-ayt42014/OfferCodeWS/WROAYT20200606043020200606202006201640L14AYT42014DZX2AA02";
        String text = "Cena wycieczki się zmieniła z " + initialPrice + " na " + newPrice + ". <br> Wejdź na <a href=" + urlToOffer + ">Alegria Hotel TUI</a>, zrób screen i wyślij do TUI.";
        emailSender.sendMail(eweMail, title, text, true);
        emailSender.sendMail(adiMail, title, text, true);
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(int initialPrice) {
        this.initialPrice = initialPrice;
    }
}
