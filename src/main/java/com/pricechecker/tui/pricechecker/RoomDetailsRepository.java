package com.pricechecker.tui.pricechecker;

import com.pricechecker.tui.pricechecker.RoomDetails;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDetailsRepository extends CrudRepository<RoomDetails, Integer> {

    List<RoomDetails> findAllByRoomCode(String roomCode);
}
