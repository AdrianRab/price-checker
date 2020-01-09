package com.pricechecker.tui.pricechecker.roomdetails;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDetailsRepository extends CrudRepository<RoomDetails, Integer> {

    List<RoomDetails> findAllByRoomCode(String roomCode);
}
