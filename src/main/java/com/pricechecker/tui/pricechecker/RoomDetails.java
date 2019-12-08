package com.pricechecker.tui.pricechecker;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="room_details")
public class RoomDetails implements Serializable {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Getter @Setter
    private String airportName;
    @Getter @Setter
    private String departureDate;
    @Getter @Setter
    private String returnDate;
    @Getter @Setter
    private int duration;
    @Getter @Setter
    private String roomName;
    @Getter @Setter
    private String roomCode;
    @Getter @Setter
    private int price;
    @Getter @Setter
    private int discountPrice;
    @Getter @Setter
    private String offerCode;
    private Date receivedOn;
    @Getter @Setter
    private String details;
    @Getter @Setter
    private int originalPrice;
    @Getter @Setter
    @ElementCollection
    @CollectionTable(name="emails", joinColumns = @JoinColumn(name="id"))
    private List<String> emails = new ArrayList<>();

    public static final class RoomDetailsBuilder {
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

        private RoomDetailsBuilder() {
        }

        public static RoomDetailsBuilder aRoomDetails() {
            return new RoomDetailsBuilder();
        }

        public RoomDetailsBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public RoomDetailsBuilder withAirportName(String airportName) {
            this.airportName = airportName;
            return this;
        }

        public RoomDetailsBuilder withDepartureDate(String departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public RoomDetailsBuilder withReturnDate(String returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public RoomDetailsBuilder withDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public RoomDetailsBuilder withRoomName(String roomName) {
            this.roomName = roomName;
            return this;
        }

        public RoomDetailsBuilder withRoomCode(String roomCode) {
            this.roomCode = roomCode;
            return this;
        }

        public RoomDetailsBuilder withPrice(int price) {
            this.price = price;
            return this;
        }

        public RoomDetailsBuilder withDiscountPrice(int discountPrice) {
            this.discountPrice = discountPrice;
            return this;
        }

        public RoomDetailsBuilder withOfferCode(String offerCode) {
            this.offerCode = offerCode;
            return this;
        }

        public RoomDetailsBuilder withReceivedOn(Date receivedOn) {
            this.receivedOn = receivedOn;
            return this;
        }

        public RoomDetailsBuilder withDetails(String details) {
            this.details = details;
            return this;
        }

        public RoomDetailsBuilder withOriginalPrice(int originalPrice) {
            this.originalPrice = originalPrice;
            return this;
        }

        public RoomDetailsBuilder withEmails(List<String> emails) {
            this.emails = emails;
            return this;
        }

        public RoomDetails build() {
            RoomDetails roomDetails = new RoomDetails();
            roomDetails.setId(id);
            roomDetails.setAirportName(airportName);
            roomDetails.setDepartureDate(departureDate);
            roomDetails.setReturnDate(returnDate);
            roomDetails.setDuration(duration);
            roomDetails.setRoomName(roomName);
            roomDetails.setRoomCode(roomCode);
            roomDetails.setPrice(price);
            roomDetails.setDiscountPrice(discountPrice);
            roomDetails.setOfferCode(offerCode);
            roomDetails.setReceivedOn(receivedOn);
            roomDetails.setDetails(details);
            roomDetails.setOriginalPrice(originalPrice);
            roomDetails.setEmails(emails);
            return roomDetails;
        }
    }

    @JsonSerialize(using = DateSerializer.class)
    public Date getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(Date receivedOn) {
        this.receivedOn = receivedOn;
    }

    @Override
    public String toString() {
        return "RoomDetails{" +
                "airportName='" + airportName + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", duration=" + duration +
                ", roomName='" + roomName + '\'' +
                ", roomCode='" + roomCode + '\'' +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                ", offerCode='" + offerCode + '\'' +
                ", receivedOn=" + receivedOn +
                ", details='" + details + '\'' +
                '}';
    }
}