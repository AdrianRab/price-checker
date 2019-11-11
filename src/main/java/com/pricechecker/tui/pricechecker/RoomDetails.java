package com.pricechecker.tui.pricechecker;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import java.io.Serializable;
import java.util.Date;

public class RoomDetails implements Serializable {
    private String airportName;
    private Date departureDate;
    private Date returnDate;
    private int duration;
    private String roomName;
    private String roomCode;
    private int price;
    private int discountPrice;
    private String offerCode;
    private Date receivedOn;

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    @JsonSerialize(using=DateSerializer.class)
    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    @JsonSerialize(using=DateSerializer.class)
    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    @JsonSerialize(using= DateSerializer.class)
    public Date getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(Date receivedOn) {
        this.receivedOn = receivedOn;
    }
}

//all offers
//{"offers":[{"airportName":"Wrocław","departureDate":"2020-09-18","departureHours":"22:25","returnDate":"2020-10-02","accommodationDate":"2020-09-18","duration":14,"boardName":"All Inclusive","roomName":"Pokój 2-osobowy","roomCode":"DZX1","price":6980,"discountPrice":6980,"offerCode":"WROADB20200918222520200918202010021945L14ADB11932DZX1AA02"},{"airportName":"Wrocław","departureDate":"2020-09-18","departureHours":"22:25","returnDate":"2020-10-02","accommodationDate":"2020-09-18","duration":14,"boardName":"All Inclusive","roomName":"Pokój 2-osobowy z widokiem na morze","roomCode":"DZM1","price":7372,"discountPrice":7372,"offerCode":"WROADB20200918222520200918202010021945L14ADB11932DZM1AA02"}],"pagination":{"pageNo":0,"totalPages":1,"pageSize":2,"totalResults":2}}