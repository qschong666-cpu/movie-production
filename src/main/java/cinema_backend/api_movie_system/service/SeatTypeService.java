package cinema_backend.api_movie_system.service;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cinema_backend.api_movie_system.models.SeatType;

public interface SeatTypeService {
     SeatType createSeatType(SeatType seatType);

   SeatType updateSeatType(SeatType seatType);

   String deletedSeatType(int seatTypeId);

   String softDeletedSeatType(int seatTypeId);

   SeatType getSeatType(int seatTypeId);

   List<SeatType> getAllSeatTypes();

   Page<SeatType> getSeatTypesPages(String search,String status,Pageable pageable);
}