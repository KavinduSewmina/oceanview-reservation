package com.room.reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.room.reservation.entity.RoomType;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    Optional<RoomType> findByName(String name);
}