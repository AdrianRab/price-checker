package com.pricechecker.tui.pricechecker;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPuller {
    public static final String ROOM_CODE = "DZX2";
    static int INITIAL_PRICE = 7322;
    private EmailSender emailSender;
    private RoomDetailsService roomDetailsService;

    @Autowired
    public DataPuller(EmailSender emailSender, RoomDetailsService roomDetailsService) {
        this.emailSender = emailSender;
        this.roomDetailsService = roomDetailsService;
    }


    RoomDetails checkPriceAndEnrichResponse(StringBuilder response) throws JsonProcessingException, MessagingException {
        RoomDetails roomDetails = RoomDetailsParser.parseJson(response.toString());
        saveToDbIfNoEntry(roomDetails);
        checkPriceAndSendNotification(roomDetails);
        return roomDetails;
    }

    private void saveToDbIfNoEntry(RoomDetails roomDetails) {
        List<Integer> allPrices = getAllSavedPricesForRoom(roomDetails.getRoomCode());

        if (!allPrices.contains(roomDetails.getPrice())) {
            roomDetailsService.save(roomDetails);
        }
    }

    private List<Integer> getAllSavedPricesForRoom(String roomCode) {
        return roomDetailsService.getAllRoomDetailsByRoomCode(roomCode)
                .stream()
                .map(RoomDetails::getPrice)
                .sorted()
                .collect(Collectors.toList());
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

    private void checkPriceAndSendNotification(RoomDetails roomDetails) throws MessagingException {
        List<Integer> prices = getAllSavedPricesForRoom(roomDetails.getRoomCode());
        int newPrice = roomDetails.getPrice();
        if (!prices.isEmpty()) {
            INITIAL_PRICE = prices.get(0);
        }
        if (INITIAL_PRICE > newPrice && !prices.contains(newPrice)) {
            prepareAndSendNotificationForLowerPrice(roomDetails);
            System.out.println(INITIAL_PRICE);
            roomDetails.setDetails("Cena jest niższa (stara " + INITIAL_PRICE + ")!!! Jedyne " + newPrice + " zł");
            return;
        } else if (INITIAL_PRICE < newPrice && !prices.contains(newPrice)) {
            prepareAndSendNotificationForHigherPrice(roomDetails);
            System.out.println(newPrice);
            roomDetails.setDetails("Cena jest wyższa (stara " + INITIAL_PRICE + ") niż podczas zakupu: " + newPrice + " zł");
            return;
        }
        roomDetails.setDetails("Cena się nie zmieniła: " + INITIAL_PRICE + " zł");
    }

    private void prepareAndSendNotificationForHigherPrice(RoomDetails roomDetails) throws MessagingException {
        String eweMail = roomDetails.getEmails().size() == 2 ? roomDetails.getEmails().get(0) : "ewe89@o2.pl";
        String adiMail = roomDetails.getEmails().size() == 2 ? roomDetails.getEmails().get(1) : "adi8912@poczta.fm";
        String title = "Wycieczka TUI - cena wzrosła!";
        String text = "Cena wycieczki się zmieniła z " + INITIAL_PRICE + " na " + roomDetails.getPrice() + ". <br> Czyli wzrosła. Słabo.";
        emailSender.sendMail(eweMail, title, text, true);
        emailSender.sendMail(adiMail, title, text, true);
    }

    private void prepareAndSendNotificationForLowerPrice(RoomDetails roomDetails) throws MessagingException {
        String eweMail = roomDetails.getEmails().size() == 2 ? roomDetails.getEmails().get(0) : "ewe89@o2.pl";
        String adiMail = roomDetails.getEmails().size() == 2 ? roomDetails.getEmails().get(1) : "adi8912@poczta.fm";
        String title = "Wycieczka TUI - cena spadła!";
        String urlToOffer = "https://www.tui.pl/wypoczynek/turcja/riwiera-turecka/side-alegria-hotel-spa-ayt42014/OfferCodeWS/WROAYT20200606043020200606202006201640L14AYT42014DZX2AA02";
        String text = "Cena wycieczki się zmieniła z " + INITIAL_PRICE + " na " + roomDetails.getPrice() + ". <br> Wejdź na <a href=" + urlToOffer + ">Alegria Hotel TUI</a>, zrób screen i wyślij do TUI.";
        emailSender.sendMail(eweMail, title, text, true);
        emailSender.sendMail(adiMail, title, text, true);
    }
}
