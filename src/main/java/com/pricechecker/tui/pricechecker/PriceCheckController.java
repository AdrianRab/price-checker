package com.pricechecker.tui.pricechecker;

import java.io.IOException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
public class PriceCheckController {

    private DataPuller dataPuller;

    @Autowired
    public PriceCheckController(DataPuller dataPuller) {
        this.dataPuller = dataPuller;
    }

    @GetMapping(produces = "application/json")
    public String getHotelPrice() throws IOException {
        return runPriceCheck();
    }

    @Scheduled(fixedRate = 3600000)
    private String runPriceCheck() throws IOException {
        URL tuiPrices = new URL("https://m.tui.pl/hotel-cards/configurators/all-offers");
        String jsonInputForAllOffers = "{\"hotelCode\":\"ADB11932\",\"tripType\":\"WS\",\"adultsCount\":\"2\",\"childrenBirthdays\":[],\"airportCode\":\"WRO\",\"startDate\":\"2020-09-18\",\"durationFrom\":\"14\",\"durationTo\":\"14\",\"boardCode\":\"A\",\"pagination\":{\"pageNo\":0,\"pageSize\":6},\"sort\":{\"field\":\"PRICE\",\"order\":\"ASCENDING\"}}";
        StringBuilder pulledRoomData = dataPuller.connectAndPullData(jsonInputForAllOffers, tuiPrices);
        return dataPuller.checkPriceAndEnrichResponse(pulledRoomData);
    }
}
