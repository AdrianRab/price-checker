package com.pricechecker.tui.pricechecker.repository;

import com.pricechecker.tui.pricechecker.RoomDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDetailsRepository extends CrudRepository<RoomDetails, Long> {
}
