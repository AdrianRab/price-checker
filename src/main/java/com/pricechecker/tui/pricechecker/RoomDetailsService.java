package com.pricechecker.tui.pricechecker;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
