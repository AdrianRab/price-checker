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