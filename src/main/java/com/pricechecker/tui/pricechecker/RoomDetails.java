package com.pricechecker.tui.pricechecker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RoomDetails implements Serializable {
    private Integer id;
    private String airportName;
    private String departureDate;
    private String returnDate;
    private int duration;
    private String roomName;
    private String roomCode;
    private int price;
    private int discountPrice;
    private String offerCode;
    private Date receivedOn;
    private String details;
    private int originalPrice;
    private List<String> emails = new ArrayList<>();
}

//all offers
//{"offers":[{"airportName":"Wrocław","departureDate":"2020-09-18","departureHours":"22:25","returnDate":"2020-10-02","accommodationDate":"2020-09-18","duration":14,"boardName":"All Inclusive","roomName":"Pokój 2-osobowy","roomCode":"DZX1","price":6980,"discountPrice":6980,"offerCode":"WROADB20200918222520200918202010021945L14ADB11932DZX1AA02"},{"airportName":"Wrocław","departureDate":"2020-09-18","departureHours":"22:25","returnDate":"2020-10-02","accommodationDate":"2020-09-18","duration":14,"boardName":"All Inclusive","roomName":"Pokój 2-osobowy z widokiem na morze","roomCode":"DZM1","price":7372,"discountPrice":7372,"offerCode":"WROADB20200918222520200918202010021945L14ADB11932DZM1AA02"}],"pagination":{"pageNo":0,"totalPages":1,"pageSize":2,"totalResults":2}}