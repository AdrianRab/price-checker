package com.pricechecker.tui.pricechecker;

import java.util.Date;

public class RoomDetailsParser {

    public static RoomDetails parse(String response, String details) {
        RoomDetails roomDetails = new RoomDetails();
        int roomPrice = Integer.valueOf(response.substring(response.indexOf(DataPuller.ROOM_CODE) + 14, response.indexOf(DataPuller.ROOM_CODE) + 18));
        int duration = Integer.valueOf(response.substring(response.indexOf(DataPuller.ROOM_CODE) - 72, response.indexOf(DataPuller.ROOM_CODE) - 70));
        int discountPrice = Integer.valueOf(response.substring(response.indexOf(DataPuller.ROOM_CODE) + 35, response.indexOf(DataPuller.ROOM_CODE) + 39));
        String roomName = response.substring(response.indexOf(DataPuller.ROOM_CODE) - 29, response.indexOf(DataPuller.ROOM_CODE) - 14);
        String returnDate = response.substring(response.indexOf(DataPuller.ROOM_CODE) - 128, response.indexOf(DataPuller.ROOM_CODE) - 118);
        String departureTime = response.substring(response.indexOf(DataPuller.ROOM_CODE) - 150, response.indexOf(DataPuller.ROOM_CODE) - 144);
        String departureDate = response.substring(response.indexOf(DataPuller.ROOM_CODE) - 179, response.indexOf(DataPuller.ROOM_CODE) - 169);
        String airport = response.substring(response.indexOf(DataPuller.ROOM_CODE) - 205, response.indexOf(DataPuller.ROOM_CODE) - 198);
        String offertCode = response.substring(response.indexOf(DataPuller.ROOM_CODE) + 55, response.indexOf(DataPuller.ROOM_CODE) + 110);

        roomDetails.setPrice(roomPrice);
        roomDetails.setDuration(duration);
        roomDetails.setRoomCode(DataPuller.ROOM_CODE);
        roomDetails.setRoomName(roomName);
        roomDetails.setReceivedOn(new Date(System.currentTimeMillis()));
        roomDetails.setReturnDate(returnDate);
        roomDetails.setDepartureDate(departureDate);
        roomDetails.setAirportName(airport);
        roomDetails.setDetails(details);
        roomDetails.setDiscountPrice(discountPrice);
        roomDetails.setOfferCode(offertCode);
        roomDetails.setOriginalPrice(DataPuller.INITIAL_PRICE);

        System.out.println(roomDetails.toString());
        return roomDetails;
    }

}
