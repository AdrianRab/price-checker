package com.pricechecker.tui.pricechecker.datapuller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pricechecker.tui.pricechecker.EmailSender;
import com.pricechecker.tui.pricechecker.offerdetails.OfferDetailsParser;
import com.pricechecker.tui.pricechecker.roomdetails.RoomDetails;
import com.pricechecker.tui.pricechecker.roomdetails.RoomDetailsParser;
import com.pricechecker.tui.pricechecker.roomdetails.RoomDetailsService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataPuller {
    public static final String ROOM_CODE = "DZX2";
    public static int INITIAL_PRICE = 7028;
    private EmailSender emailSender;
    private RoomDetailsService roomDetailsService;
    private final String GET = "GET";
    private final String POST = "POST";

    @Autowired
    public DataPuller(EmailSender emailSender, RoomDetailsService roomDetailsService) {
        this.emailSender = emailSender;
        this.roomDetailsService = roomDetailsService;
    }

    public RoomDetails checkPriceAndEnrichResponse(StringBuilder response) throws JsonProcessingException, MessagingException {
        log.info("Checking price from POST method");
        RoomDetails roomDetails = RoomDetailsParser.parseJson(response.toString());
        checkPriceAndSendNotification(roomDetails);
        roomDetailsService.saveToDbIfNoEntry(roomDetails);
        return roomDetails;
    }

    public RoomDetails checkPriceAndConvertResponse(StringBuilder pulledOfferData) throws JsonProcessingException, MessagingException {
        log.info("Checking price from GET method");
        RoomDetails roomDetails = OfferDetailsParser.parseJson(pulledOfferData.toString());
        checkPriceAndSendNotification(roomDetails);
        roomDetailsService.saveToDbIfNoEntry(roomDetails);
        return roomDetails;
    }


    public StringBuilder connectAndPullData(String jsonBodyInputString, URL tuiPrices) throws IOException {
        HttpURLConnection connection = getHttpURLConnection(tuiPrices, POST);
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = jsonBodyInputString.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        StringBuilder result = new StringBuilder();

        log.info("Response code: {} ; Response message: {}", connection.getResponseCode(), connection.getResponseMessage());

        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = in.readLine()) != null)
                result.append(responseLine);
        }
        log.info(result.toString());
        return result;
    }

    public StringBuilder connectAndPullDataGet(URL tuiOffer) throws IOException {
        HttpURLConnection connection = getHttpURLConnection(tuiOffer, GET);

        StringBuilder result = new StringBuilder();

        log.info("Response code: {} ; Response message: {}", connection.getResponseCode(), connection.getResponseMessage());

        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = in.readLine()) != null)
                result.append(responseLine);
        }
        log.info(result.toString());
        return result;
    }


    private HttpURLConnection getHttpURLConnection(URL url, String method) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        return connection;
    }

    private void checkPriceAndSendNotification(RoomDetails roomDetails) throws MessagingException {
        List<Integer> sortedPrices = roomDetailsService.getAllSavedPricesForRoom(roomDetails.getRoomCode());
        int newPrice = roomDetails.getPrice();
        if (INITIAL_PRICE > newPrice && !sortedPrices.contains(newPrice)) {
            emailSender.prepareAndSendNotificationForLowerPrice(roomDetails, INITIAL_PRICE);
            roomDetails.setDetails("Cena jest niższa (stara " + INITIAL_PRICE + ")!!! Jedyne " + newPrice + " zł");
        } else if (INITIAL_PRICE < newPrice && !sortedPrices.contains(newPrice)) {
            emailSender.prepareAndSendNotificationForHigherPrice(roomDetails, INITIAL_PRICE);
            roomDetails.setDetails("Cena jest wyższa (stara " + INITIAL_PRICE + ") niż podczas zakupu: " + newPrice + " zł");
        } else if (sortedPrices.contains(newPrice) || INITIAL_PRICE == newPrice){
            log.info("Price is already saved in db, skipping e-mail sending");
            roomDetails.setDetails("Cena się nie zmieniła: " + newPrice + " zł");
        }

        if (newPrice < INITIAL_PRICE && (!sortedPrices.isEmpty() && newPrice < sortedPrices.get(0))) {
            //TODO change logic to make sure initial price is changed only when price is lowest globally.
            log.info("New price {} is lower than initial {}, changing initial price", newPrice, INITIAL_PRICE);
            INITIAL_PRICE = newPrice;
        }
    }

    public static void setInitialPrice(int initialPrice) {
        INITIAL_PRICE = initialPrice;
    }
}
