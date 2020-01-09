package com.pricechecker.tui.pricechecker.datapuller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pricechecker.tui.pricechecker.EmailSender;
import com.pricechecker.tui.pricechecker.roomdetails.RoomDetails;
import com.pricechecker.tui.pricechecker.roomdetails.RoomDetailsService;
import javax.mail.MessagingException;
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
    @Mock
    private RoomDetailsService roomDetailsService;

    @BeforeEach
    void init() {
        EmailSender emailSender = new EmailSender(javaMailSender);
        dataPuller = new DataPuller(emailSender, roomDetailsService);
        dataPuller.setInitialPrice(7322);
    }

    @Test
    void shouldCheckPriceAndSendNotificationIfLower() throws JsonProcessingException, MessagingException {
        StringBuilder responseWithLowerPrice = new StringBuilder("{\"offers\":[{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ1\",\"price\":6902,\"discountPrice\":6902,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ2\",\"price\":7000,\"discountPrice\":7000,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX1\",\"price\":7196,\"discountPrice\":7196,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX2\",\"price\":7190,\"discountPrice\":7290,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy z widokiem na morze\",\"roomCode\":\"DZM1\",\"price\":7782,\"discountPrice\":7782,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZM1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Suita\",\"roomCode\":\"SUX1\",\"price\":8186,\"discountPrice\":8186,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014SUX1AA02\"}],\"pagination\":{\"pageNo\":0,\"totalPages\":1,\"pageSize\":6,\"totalResults\":6}}");

        when(javaMailSender.createMimeMessage()).thenReturn(message);
        doNothing().when(javaMailSender).send(message);
        RoomDetails result = dataPuller.checkPriceAndEnrichResponse(responseWithLowerPrice);

        assertEquals("Cena jest niższa (stara 7322)!!! Jedyne 7190 zł", result.getDetails());
    }

    @Test
    void shouldCheckPriceAndSendNotificationIfHigher() throws JsonProcessingException, MessagingException {
        StringBuilder responseWitHigherPrice = new StringBuilder("{\"offers\":[{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ1\",\"price\":6902,\"discountPrice\":6902,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ2\",\"price\":7000,\"discountPrice\":7000,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX1\",\"price\":7196,\"discountPrice\":7196,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX2\",\"price\":7590,\"discountPrice\":7590,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy z widokiem na morze\",\"roomCode\":\"DZM1\",\"price\":7782,\"discountPrice\":7782,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZM1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Suita\",\"roomCode\":\"SUX1\",\"price\":8186,\"discountPrice\":8186,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014SUX1AA02\"}],\"pagination\":{\"pageNo\":0,\"totalPages\":1,\"pageSize\":6,\"totalResults\":6}}");

        when(javaMailSender.createMimeMessage()).thenReturn(message);
        doNothing().when(javaMailSender).send(message);
        RoomDetails result = dataPuller.checkPriceAndEnrichResponse(responseWitHigherPrice);

        assertEquals("Cena jest wyższa (stara 7322) niż podczas zakupu: 7590 zł", result.getDetails());
    }

    @Test
    void shouldCheckPriceAndNotSendNotification() throws JsonProcessingException, MessagingException {
        StringBuilder unchangedPrice = new StringBuilder("{\"offers\":[{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ1\",\"price\":6902,\"discountPrice\":6902,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ2\",\"price\":7000,\"discountPrice\":7000,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX1\",\"price\":7196,\"discountPrice\":7196,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX2\",\"price\":7322,\"discountPrice\":7322,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy z widokiem na morze\",\"roomCode\":\"DZM1\",\"price\":7782,\"discountPrice\":7782,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZM1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Suita\",\"roomCode\":\"SUX1\",\"price\":8186,\"discountPrice\":8186,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014SUX1AA02\"}],\"pagination\":{\"pageNo\":0,\"totalPages\":1,\"pageSize\":6,\"totalResults\":6}}");

        RoomDetails result = dataPuller.checkPriceAndEnrichResponse(unchangedPrice);

        assertEquals("Cena się nie zmieniła: 7322 zł", result.getDetails());
    }
}