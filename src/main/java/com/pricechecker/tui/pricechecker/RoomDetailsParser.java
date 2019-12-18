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
        return RoomDetails.RoomDetailsBuilder.aRoomDetails()
                .withRoomCode(DataPuller.ROOM_CODE)
                .withAirportName(myRoomDetails.get(0).getAirportName())
                .withDepartureDate(myRoomDetails.get(0).getDepartureDate())
                .withDiscountPrice(myRoomDetails.get(0).getDiscountPrice())
                .withDuration(myRoomDetails.get(0).getDuration())
                .withRoomName(myRoomDetails.get(0).getRoomName())
                .withOfferCode(myRoomDetails.get(0).getOfferCode())
                .withReturnDate(myRoomDetails.get(0).getReturnDate())
                .withOriginalPrice(DataPuller.INITIAL_PRICE)
                .withReceivedOn(new Date(System.currentTimeMillis()))
                .withPrice(myRoomDetails.get(0).getPrice())
                .withEmails(Arrays.asList("ewe89@o2.pl", "adi8912@poczta.fm"))
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
