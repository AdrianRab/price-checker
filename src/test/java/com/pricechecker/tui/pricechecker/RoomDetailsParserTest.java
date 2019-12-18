package com.pricechecker.tui.pricechecker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomDetailsParserTest {

    @Test
    @DisplayName("Should parse response to RoomDetails object")
    public void shouldParseResponse() throws JsonProcessingException {
        //given
        String response = "{\"offers\":[{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ1\",\"price\":6902,\"discountPrice\":6902,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ2\",\"price\":7000,\"discountPrice\":7000,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX1\",\"price\":7196,\"discountPrice\":7196,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX2\",\"price\":7190,\"discountPrice\":7290,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy z widokiem na morze\",\"roomCode\":\"DZM1\",\"price\":7782,\"discountPrice\":7782,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZM1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Suita\",\"roomCode\":\"SUX1\",\"price\":8186,\"discountPrice\":8186,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014SUX1AA02\"}],\"pagination\":{\"pageNo\":0,\"totalPages\":1,\"pageSize\":6,\"totalResults\":6}}";

        //when
        RoomDetails result = RoomDetailsParser.parseJson(response);

        //then
        assertEquals(7190, result.getPrice());
        assertEquals("Pokój 2-osobowy", result.getRoomName());
        assertEquals("Wrocław", result.getAirportName());
        assertEquals("2020-06-06", result.getDepartureDate());
    }

    @Test
    @DisplayName("Should throw exception when room is not available")
    public void shouldNotParseResponse_ExceptionExpected() {
        //given
        String response = "{\"offers\":[{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ1\",\"price\":6902,\"discountPrice\":6902,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój ekonomiczny\",\"roomCode\":\"DZZ2\",\"price\":7000,\"discountPrice\":7000,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZZ2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DZX1\",\"price\":7196,\"discountPrice\":7196,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZX1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy\",\"roomCode\":\"DXZ2\",\"price\":7190,\"discountPrice\":7290,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DXZ2AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Pokój 2-osobowy z widokiem na morze\",\"roomCode\":\"DZM1\",\"price\":7782,\"discountPrice\":7782,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014DZM1AA02\"},{\"airportName\":\"Wrocław\",\"departureDate\":\"2020-06-06\",\"departureHours\":\"04:30\",\"returnDate\":\"2020-06-20\",\"accommodationDate\":\"2020-06-06\",\"duration\":14,\"boardName\":\"All Inclusive\",\"roomName\":\"Suita\",\"roomCode\":\"SUX1\",\"price\":8186,\"discountPrice\":8186,\"offerCode\":\"WROAYT20200606043020200606202006201640L14AYT42014SUX1AA02\"}],\"pagination\":{\"pageNo\":0,\"totalPages\":1,\"pageSize\":6,\"totalResults\":6}}";

        //when then
        assertThrows(IllegalStateException.class, () -> RoomDetailsParser.parseJson(response));
    }

}