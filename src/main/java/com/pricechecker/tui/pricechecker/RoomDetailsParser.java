package com.pricechecker.tui.pricechecker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RoomDetailsParser {

    public static RoomDetails parseJson(String response) throws JsonProcessingException {
        List<ResponseDetails> myRoomDetails = parseResponseAndGetOfferDetails(response);
        return getRoomDetails(myRoomDetails);
    }

    private static RoomDetails getRoomDetails(List<ResponseDetails> myRoomDetails) {
        if (myRoomDetails.isEmpty()) {
            throw new IllegalStateException("Your room" + DataPuller.ROOM_CODE + "is not available.");
        }
        return RoomDetails.builder()
                .roomCode(DataPuller.ROOM_CODE)
                .airportName(myRoomDetails.get(0).getAirportName())
                .departureDate(myRoomDetails.get(0).getDepartureDate())
                .discountPrice(myRoomDetails.get(0).getDiscountPrice())
                .duration(myRoomDetails.get(0).getDuration())
                .roomName(myRoomDetails.get(0).getRoomName())
                .offerCode(myRoomDetails.get(0).getOfferCode())
                .returnDate(myRoomDetails.get(0).getReturnDate())
                .originalPrice(DataPuller.INITIAL_PRICE)
                .receivedOn(new Date(System.currentTimeMillis()))
                .price(myRoomDetails.get(0).getPrice())
                .emails(Arrays.asList("ewe89@o2.pl", "adi8912@poczta.fm"))
                .build();
    }

    private static List<ResponseDetails> parseResponseAndGetOfferDetails(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response);
        JsonNode offers = node.get("offers");
        List<ResponseDetails> responseDetailsList = new ArrayList<>();
        for (JsonNode offer : offers) {
            ResponseDetails responseDetails = objectMapper.treeToValue(offer, ResponseDetails.class);
            responseDetailsList.add(responseDetails);
        }

        return responseDetailsList
                .stream()
                .filter(respDetails -> respDetails.getRoomCode().equals(DataPuller.ROOM_CODE))
                .collect(Collectors.toList());
    }

}
