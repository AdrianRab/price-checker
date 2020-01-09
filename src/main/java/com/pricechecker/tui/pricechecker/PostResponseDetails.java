package com.pricechecker.tui.pricechecker;
import lombok.Data;

@Data
public class PostResponseDetails {
    private String airportName;
    private String departureDate;
    private String departureHours;
    private String returnDate;
    private String accommodationDate;
    private int duration;
    private String boardName;
    private String roomName;
    private String roomCode;
    private int price;
    private int discountPrice;
    private String offerCode;
}
