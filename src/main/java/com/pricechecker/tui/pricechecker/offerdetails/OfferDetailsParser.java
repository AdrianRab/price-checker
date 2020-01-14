package com.pricechecker.tui.pricechecker.offerdetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricechecker.tui.pricechecker.datapuller.DataPuller;
import com.pricechecker.tui.pricechecker.roomdetails.RoomDetails;
import java.util.Arrays;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OfferDetailsParser {
    private static final int MANDATORY_INSURANCE = 30;

    public static RoomDetails parseJson(String response) throws JsonProcessingException {
        validateIfRoomCodeIsCorrect(response);
        return parseResponseAndGetOfferDetails(response);

    }

    private static void validateIfRoomCodeIsCorrect(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response);
        String roomCode = root.at("/offerData/roomData/code").textValue();
        if (!DataPuller.ROOM_CODE.equals(roomCode)) {
            log.debug("Room {} is not available. Available room is {} ", DataPuller.ROOM_CODE, roomCode);
            throw new IllegalStateException("Your room " + DataPuller.ROOM_CODE + " is not available. Available room is " + roomCode);
        }
    }

    private static RoomDetails parseResponseAndGetOfferDetails(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("Parsing response to OfferDetails object");
        JsonNode root = objectMapper.readTree(response);
        int price = root.at("/priceInformationData/priceData/amount").asInt();
        int discountPrice = root.at("/priceInformationData/priceData/discountAmount").asInt();
        String startDate = root.at("/offerData/startDate").textValue();
        String endDate = root.at("/offerData/endDate").textValue();
        int duration = root.at("/offerData/accommodation").asInt();
        String roomCode = root.at("/offerData/roomData/code").textValue();
        String roomName = root.at("/offerData/roomData/name").textValue();
        String airportName = root.at("/offerData/departurePlaceName").textValue();

        int priceWithInsurance = price + MANDATORY_INSURANCE;
        int discountPriceWithInsurance = discountPrice + MANDATORY_INSURANCE;

        return RoomDetails.builder()
                .roomCode(roomCode)
                .airportName(airportName)
                .departureDate(startDate)
                .discountPrice(discountPriceWithInsurance)
                .duration(duration)
                .roomName(roomName)
                .offerCode("No offer code available")
                .returnDate(endDate)
                .originalPrice(DataPuller.INITIAL_PRICE)
                .receivedOn(new Date(System.currentTimeMillis()))
                .price(priceWithInsurance)
                .emails(Arrays.asList("ewe89@o2.pl", "adi8912@poczta.fm"))
                .build();
    }
}