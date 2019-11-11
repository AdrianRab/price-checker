package com.pricechecker.tui.pricechecker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import javax.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class DataPullerTest {

    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private MimeMessage message;
    private DataPuller dataPuller;

    @BeforeEach
    void init() {
        EmailSender emailSender = new EmailSender(javaMailSender);
        dataPuller = new DataPuller(emailSender);
    }

    @Test
    void shouldCheckPriceAndSendNotification() {
        StringBuilder responseWithLowerPrice = new StringBuilder("{\"offers\":[{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-09-18\",\"departureHours\":\"22:25\",\"returnDate\":\"2020-10-02\",\"accommodationDate\":\"2020-09-18\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX1\",\"price\":6980,\"discountPrice\":6980,\"offerCode\":\"WROADB20200918222520200918202010021945L14ADB11932DZX1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-09-18\",\"departureHours\":\"22:25\",\"returnDate\":\"2020-10-02\",\"accommodationDate\":\"2020-09-18\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy z widokiem na morze\",\"roomCode\":\"DZM1\",\"price\":7172,\"discountPrice\":7172,\"offerCode\":\"WROADB20200918222520200918202010021945L14ADB11932DZM1AA02\"}],\"pagination\":{\"pageNo\":0,\"totalPages\":1,\"pageSize\":2,\"totalResults\":2}}\n");

        when(javaMailSender.createMimeMessage()).thenReturn(message);
        doNothing().when(javaMailSender).send(message);
        String result = dataPuller.checkPriceAndEnrichResponse(responseWithLowerPrice);

        assertTrue(result.contains("Cena jest niższa!!!"));
    }

    @Test
    void shouldCheckPriceAndNotSendNotification() {
        StringBuilder responseWithLowerPrice = new StringBuilder("{\"offers\":[{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-09-18\",\"departureHours\":\"22:25\",\"returnDate\":\"2020-10-02\",\"accommodationDate\":\"2020-09-18\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX1\",\"price\":6980,\"discountPrice\":6980,\"offerCode\":\"WROADB20200918222520200918202010021945L14ADB11932DZX1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-09-18\",\"departureHours\":\"22:25\",\"returnDate\":\"2020-10-02\",\"accommodationDate\":\"2020-09-18\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy z widokiem na morze\",\"roomCode\":\"DZM1\",\"price\":7372,\"discountPrice\":7372,\"offerCode\":\"WROADB20200918222520200918202010021945L14ADB11932DZM1AA02\"}],\"pagination\":{\"pageNo\":0,\"totalPages\":1,\"pageSize\":2,\"totalResults\":2}}\n");

        String result = dataPuller.checkPriceAndEnrichResponse(responseWithLowerPrice);

        assertTrue(result.contains("Cena się nie zmieniła"));
    }

    @Test
    void shouldOverrideOldPriceWhenNewIsLower() {
        StringBuilder responseWithLowerPrice = new StringBuilder("{\"offers\":[{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-09-18\",\"departureHours\":\"22:25\",\"returnDate\":\"2020-10-02\",\"accommodationDate\":\"2020-09-18\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX1\",\"price\":6980,\"discountPrice\":6980,\"offerCode\":\"WROADB20200918222520200918202010021945L14ADB11932DZX1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-09-18\",\"departureHours\":\"22:25\",\"returnDate\":\"2020-10-02\",\"accommodationDate\":\"2020-09-18\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy z widokiem na morze\",\"roomCode\":\"DZM1\",\"price\":7172,\"discountPrice\":7172,\"offerCode\":\"WROADB20200918222520200918202010021945L14ADB11932DZM1AA02\"}],\"pagination\":{\"pageNo\":0,\"totalPages\":1,\"pageSize\":2,\"totalResults\":2}}\n");

        when(javaMailSender.createMimeMessage()).thenReturn(message);
        doNothing().when(javaMailSender).send(message);
        dataPuller.checkPriceAndEnrichResponse(responseWithLowerPrice);

        assertEquals(7172, dataPuller.getInitialPrice());
    }
}