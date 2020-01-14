package com.pricechecker.tui.pricechecker.roomdetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricechecker.tui.pricechecker.response.PostResponseDetails;
import com.pricechecker.tui.pricechecker.datapuller.DataPuller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoomDetailsParser {

    public static RoomDetails parseJson(String response) throws JsonProcessingException {
        List<PostResponseDetails> myRoomDetails = parseResponseAndGetOfferDetails(response);
        return getRoomDetails(myRoomDetails);
    }

    private static RoomDetails getRoomDetails(List<PostResponseDetails> myRoomDetails) {
        if (myRoomDetails.isEmpty()) {
            log.debug("Room {} is not available ", DataPuller.ROOM_CODE);
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

    private static List<PostResponseDetails> parseResponseAndGetOfferDetails(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response);
        JsonNode offers = node.get("offers");
        log.info("Parsing response to ResponseDetails object");
        List<PostResponseDetails> postResponseDetailsList = new ArrayList<>();
        for (JsonNode offer : offers) {
            PostResponseDetails postResponseDetails = objectMapper.treeToValue(offer, PostResponseDetails.class);
            postResponseDetailsList.add(postResponseDetails);
        }

        return postResponseDetailsList
                .stream()
                .filter(respDetails -> respDetails.getRoomCode().equals(DataPuller.ROOM_CODE))
                .collect(Collectors.toList());
    }

}
