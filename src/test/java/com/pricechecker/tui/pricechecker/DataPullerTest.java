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
    void shouldCheckPriceAndSendNotificationIfLower() {
        StringBuilder responseWithLowerPrice = new StringBuilder("{\"offers\":[{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ1\",\"price\":6902,\"discountPrice\":6902,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ2\",\"price\":7000,\"discountPrice\":7000,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX1\",\"price\":7196,\"discountPrice\":7196,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX2\",\"price\":7190,\"discountPrice\":7290,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy z widokiem na morze\",\"roomCode\":\"DZM1\",\"price\":7782,\"discountPrice\":7782,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZM1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Suita\",\"roomCode\":\"SUX1\",\"price\":8186,\"discountPrice\":8186,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014SUX1AA02\"}],\"pagination\":{\"pageNo\":0,\"totalPages\":1,\"pageSize\":6,\"totalResults\":6}}");

        when(javaMailSender.createMimeMessage()).thenReturn(message);
        doNothing().when(javaMailSender).send(message);
        String result = dataPuller.checkPriceAndEnrichResponse(responseWithLowerPrice);

        assertTrue(result.contains("Cena jest niższa!!!"));
    }

    @Test
    void shouldCheckPriceAndSendNotificationIfHigher() {
        StringBuilder responseWitHigherPrice = new StringBuilder("{\"offers\":[{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ1\",\"price\":6902,\"discountPrice\":6902,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ2\",\"price\":7000,\"discountPrice\":7000,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX1\",\"price\":7196,\"discountPrice\":7196,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX2\",\"price\":7590,\"discountPrice\":7590,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy z widokiem na morze\",\"roomCode\":\"DZM1\",\"price\":7782,\"discountPrice\":7782,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZM1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Suita\",\"roomCode\":\"SUX1\",\"price\":8186,\"discountPrice\":8186,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014SUX1AA02\"}],\"pagination\":{\"pageNo\":0,\"totalPages\":1,\"pageSize\":6,\"totalResults\":6}}");

        when(javaMailSender.createMimeMessage()).thenReturn(message);
        doNothing().when(javaMailSender).send(message);
        String result = dataPuller.checkPriceAndEnrichResponse(responseWitHigherPrice);

        assertTrue(result.contains("Cena jest wyższa niż podczas zakupu."));
    }

    @Test
    void shouldCheckPriceAndNotSendNotification() {
        StringBuilder unchangedPrice = new StringBuilder("{\"offers\":[{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ1\",\"price\":6902,\"discountPrice\":6902,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ2\",\"price\":7000,\"discountPrice\":7000,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX1\",\"price\":7196,\"discountPrice\":7196,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX2\",\"price\":7490,\"discountPrice\":7290,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy z widokiem na morze\",\"roomCode\":\"DZM1\",\"price\":7782,\"discountPrice\":7782,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZM1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Suita\",\"roomCode\":\"SUX1\",\"price\":8186,\"discountPrice\":8186,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014SUX1AA02\"}],\"pagination\":{\"pageNo\":0,\"totalPages\":1,\"pageSize\":6,\"totalResults\":6}}");

        String result = dataPuller.checkPriceAndEnrichResponse(unchangedPrice);

        assertTrue(result.contains("Cena się nie zmieniła"));
    }

    @Test
    void shouldOverrideOldPriceWhenNewIsLower() {
        StringBuilder responseWithLowerPrice = new StringBuilder("{\"offers\":[{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ1\",\"price\":6902,\"discountPrice\":6902,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ2\",\"price\":7000,\"discountPrice\":7000,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX1\",\"price\":7196,\"discountPrice\":7196,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX2\",\"price\":7172,\"discountPrice\":7290,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy z widokiem na morze\",\"roomCode\":\"DZM1\",\"price\":7782,\"discountPrice\":7782,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZM1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Suita\",\"roomCode\":\"SUX1\",\"price\":8186,\"discountPrice\":8186,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014SUX1AA02\"}],\"pagination\":{\"pageNo\":0,\"totalPages\":1,\"pageSize\":6,\"totalResults\":6}}");

        when(javaMailSender.createMimeMessage()).thenReturn(message);
        doNothing().when(javaMailSender).send(message);
        dataPuller.checkPriceAndEnrichResponse(responseWithLowerPrice);

        assertEquals(7172, dataPuller.getInitialPrice());
    }
}