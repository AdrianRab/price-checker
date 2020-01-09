package com.pricechecker.tui.pricechecker.roomdetails;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoomDetailsService {
    private RoomDetailsRepository roomDetailsRepository;

    public RoomDetailsService(@Autowired RoomDetailsRepository roomDetailsRepository) {
        this.roomDetailsRepository = roomDetailsRepository;
    }

    public RoomDetails save(RoomDetails roomDetails) {
        return roomDetailsRepository.save(roomDetails);
    }

    public Optional<RoomDetails> getRoomDetails(Integer id) {
        return roomDetailsRepository.findById(id);
    }

    public List<RoomDetails> getAllRoomDetails (){
        return (List<RoomDetails>) roomDetailsRepository.findAll();
    }

    public List<RoomDetails> getAllRoomDetailsByRoomCode (String roomCode){
        return roomDetailsRepository.findAllByRoomCode(roomCode);
    }

    public void saveToDbIfNoEntry(RoomDetails roomDetails) {
        List<Integer> allPrices = getAllSavedPricesForRoom(roomDetails.getRoomCode());

        if (!allPrices.contains(roomDetails.getPrice())) {
            log.info("Saving RoomDetails with new price {} to db", roomDetails.getPrice());
            save(roomDetails);
        }
        log.info("Price {} already present in database {}", roomDetails.getPrice(), allPrices.toString());
    }

    public List<Integer> getAllSavedPricesForRoom(String roomCode) {
        return getAllRoomDetailsByRoomCode(roomCode)
                .stream()
                .map(RoomDetails::getPrice)
                .sorted()
                .collect(Collectors.toList());
    }

}
