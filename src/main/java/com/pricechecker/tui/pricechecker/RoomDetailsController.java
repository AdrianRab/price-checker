package com.pricechecker.tui.pricechecker;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/details")
public class RoomDetailsController {
    private RoomDetailsRepository roomDetailsRepository;

    public RoomDetailsController(@Autowired RoomDetailsRepository roomDetailsRepository){
        this.roomDetailsRepository = roomDetailsRepository;
    }

    @GetMapping("/{id}")
    public RoomDetails getRoomDetailsById(@PathVariable Integer id){
        return roomDetailsRepository.findById(id).get();
    }

    @GetMapping()
    public List<RoomDetails> allRooms(){
        return (List<RoomDetails>) roomDetailsRepository.findAll();
    }

    @PostMapping()
    public RoomDetails save(@RequestBody RoomDetails roomDetails){
        return roomDetailsRepository.save(roomDetails);
    }

    @PutMapping("/{id}")
    public RoomDetails update(@RequestBody RoomDetails roomDetails, @PathVariable Integer id){
        Optional<RoomDetails>  persistedDetails = roomDetailsRepository.findById(id);
        if(persistedDetails.isPresent()){
            RoomDetails detailsToUpdate = persistedDetails.get();
            //TODO logika dodająca wszystkie pola.
            return roomDetailsRepository.save(detailsToUpdate);
        }
        return persistedDetails.get();
    }
}
