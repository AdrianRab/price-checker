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
@Builder(toBuilder = true)
@Entity
@Table(name="room_details")
public class RoomDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @ElementCollection
    @CollectionTable(name="emails", joinColumns = @JoinColumn(name="id"))
    private List<String> emails = new ArrayList<>();

    }
}