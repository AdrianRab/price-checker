package com.pricechecker.tui.pricechecker;

import java.io.IOException;
import java.net.URL;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
public class PriceCheckController {

    private DataPuller dataPuller;

    @GetMapping
    public String getHotelPrice() throws IOException {
        return runPriceCheck();
    }

    @Scheduled(fixedRate = 3600000)
    public String runPriceCheck() throws IOException {
        dataPuller = new DataPuller();
        URL tuiPrices = new URL("https://m.tui.pl/hotel-cards/configurators/price-calendar");
        String jsonInputString = "{\n" +
                "    \"hotelCode\": \"ADB11932\",\n" +
                "    \"tripType\": \"WS\",\n" +
                "    \"adultsCount\": \"2\",\n" +
                "    \"childrenBirthdays\": [],\n" +
                "    \"airportCode\": \"WRO\",\n" +
                "    \"startDate\": \"2020-09-18\",\n" +
                "    \"durationFrom\": \"14\",\n" +
                "    \"durationTo\": \"14\",\n" +
                "    \"boardCode\": \"A\"\n" +
                "}";
        return dataPuller.connectAndPullData(jsonInputString, tuiPrices);
    }
}
