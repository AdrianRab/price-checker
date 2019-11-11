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
    private int initialPrice = 7372;
    private EmailSender emailSender;

    @Autowired
    public DataPuller(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    String checkPriceAndEnrichResponse(StringBuilder response) {
        response.append("\n");
        response.append(checkPriceAndSendNotification(response.toString()));
        response.append("\n");
        response.append(getCurrentDate());
        return response.toString();
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

    private String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        return "Czas sprawdzenia ceny " + formatter.format(date);
    }

    private String checkPriceAndSendNotification(String response) {
        try {
            int newPrice = Integer.valueOf(response.substring(response.indexOf("DZM1") + 14, response.indexOf("DZM1") + 18));
            return comparePrice(newPrice);
        } catch (NumberFormatException ex) {
            throw new IllegalStateException("Wrong number format, check response and parsing", ex);
        } catch (MessagingException ex) {
            throw new IllegalStateException("Something is wrong with message you try to send", ex);
        }
    }

    private String comparePrice(int newPrice) throws MessagingException {
        if (initialPrice > newPrice) {
            String eweMail = "ewe89@o2.pl";
            String adiMail = "adi8912@poczta.fm";
            String title = "Wycieczka TUI - zmiana ceny!";
            String urlToOffer = "https://www.tui.pl/wypoczynek/turcja/turcja-egejska/richmond-ephesus-hotel-adb11932/OfferCodeWS/WROADB20200918222520200918202010021945L14ADB11932DZM1A";
            String text = "Cena wycieczki się zmieniła z " + initialPrice + " na " + newPrice + ". <br> Wejdź na <a href=" + urlToOffer + ">Richmond Hotel TUI</a>, zrób screen i wyślij do TUI.";
            emailSender.sendMail(eweMail, title, text, true);
            emailSender.sendMail(adiMail, title, text, true);
            initialPrice = newPrice;
            System.out.println(initialPrice);
            return "Cena jest niższa!!!";
        }
        return "Cena się nie zmieniła";
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(int initialPrice) {
        this.initialPrice = initialPrice;
    }
}
