package com.pricechecker.tui.pricechecker;

import com.pricechecker.tui.pricechecker.datapuller.DataPuller;
import com.pricechecker.tui.pricechecker.roomdetails.RoomDetails;
import java.io.IOException;
import java.net.URL;
import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class PriceCheckController {

    private DataPuller dataPuller;

    @Autowired
    public PriceCheckController(DataPuller dataPuller) {
        this.dataPuller = dataPuller;
    }

    @GetMapping
    public String pingApplication(){
        return "Application is up and running";
    }

    @GetMapping(path="/price", produces = "application/json")
    public RoomDetails getHotelPrice() throws IOException, MessagingException {
        return runPriceCheck();
    }

    @Scheduled(fixedRate = 3600000)
    private RoomDetails runPriceCheck() throws IOException, MessagingException {
        log.info("PRunning price check");
        URL tuiPrices = new URL("https://m.tui.pl/hotel-cards/configurators/all-offers");
        URL tuiOffer = new URL("https://www.tui.pl/hotel-cards/offers?offerCode=WROAYT20200606043020200606202006201640L14AYT42014DZX2AA02");
        String jsonInputForAllOffers = "{\"hotelCode\":\"AYT42014\",\"tripType\":\"WS\",\"adultsCount\":\"2\",\"childrenBirthdays\":[],\"airportCode\":\"WRO\",\"startDate\":\"2020-06-06\",\"durationFrom\":\"14\",\"durationTo\":\"14\",\"boardCode\":null,\"pagination\":{\"pageNo\":0,\"totalPages\":7,\"pageSize\":6,\"totalResults\":42},\"sort\":{\"field\":\"PRICE\",\"order\":\"ASCENDING\"}}";
        StringBuilder pulledRoomData = dataPuller.connectAndPullData(jsonInputForAllOffers, tuiPrices);
//        StringBuilder pulledOfferData = dataPuller.connectAndPullDataGet(tuiOffer);
        return dataPuller.checkPriceAndEnrichResponse(pulledRoomData);
    }
}
