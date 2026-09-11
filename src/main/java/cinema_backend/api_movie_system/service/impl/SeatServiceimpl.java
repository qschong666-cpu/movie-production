package cinema_backend.api_movie_system.service.impl;

import cinema_backend.api_movie_system.exception.*;
import cinema_backend.api_movie_system.models.Room;
import cinema_backend.api_movie_system.models.Seat;
import cinema_backend.api_movie_system.models.SeatLayoutRequest;
import cinema_backend.api_movie_system.models.SeatType;
import cinema_backend.api_movie_system.repository.RoomRepository;
import cinema_backend.api_movie_system.repository.SeatRepository;
import cinema_backend.api_movie_system.repository.SeatTypeRepository;
import cinema_backend.api_movie_system.service.SeatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatServiceimpl implements SeatService {

    private final SeatRepository seatRepository;
    private final RoomRepository roomRepository;
    private final SeatTypeRepository seatTypeRepository;

    public SeatServiceimpl(SeatRepository seatRepository, RoomRepository roomRepository, 
                           SeatTypeRepository seatTypeRepository) {
        this.seatRepository = seatRepository;
        this.roomRepository = roomRepository;
        this.seatTypeRepository = seatTypeRepository;
    }

    @Override
    public List<Seat> getSeatsByRoom(Integer roomId) {
        return seatRepository.findBySeatRoomIdAndStatusTrue(roomId);
    }

    @Override
    public Seat getSeatById(Integer id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found with id: " + id));
    }

    @Override
    @Transactional
    public Seat createSeat(Seat seat) {
        if (seat.getSeatRoom() != null && seat.getSeatRoom().getId() != null) {
            Room room = roomRepository.findById(seat.getSeatRoom().getId())
                    .orElseThrow(() -> new RoomNotFoundException("Room not found"));
            seat.setSeatRoom(room);
        }

        if (seat.getSeatType() != null && seat.getSeatType().getId() != null) {
            SeatType seatType = seatTypeRepository.findById(seat.getSeatType().getId())
                    .orElseThrow(() -> new SeatNotFoundException("Seat type not found"));
            seat.setSeatType(seatType);
        }

        seat.setStatus(true);
        return seatRepository.save(seat);
    }

    @Override
    @Transactional
    public List<Seat> generateSeatsForRoom(Integer roomId, SeatLayoutRequest layoutRequest) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new SeatNotFoundException("Room not found"));

        // Delete existing seats if any
        List<Seat> existingSeats = seatRepository.findBySeatRoomId(roomId);
        if (!existingSeats.isEmpty()) {
            seatRepository.deleteAll(existingSeats);
        }

        // Find default seat type or use first available
        SeatType defaultSeatType = null;
        if (layoutRequest.getDefaultSeatTypeId() != null) {
            defaultSeatType = seatTypeRepository.findById(layoutRequest.getDefaultSeatTypeId()).orElse(null);
        }
        if (defaultSeatType == null) {
            defaultSeatType = seatTypeRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new SeatNotFoundException("No seat types available"));
        }

        List<Seat> generatedSeats = new ArrayList<>();
        int seatCounter = 1;

        for (int row = 0; row < layoutRequest.getRows(); row++) {
            String rowLabel = String.valueOf((char) ('A' + row));
            for (int col = 1; col <= layoutRequest.getColumns(); col++) {
                Seat seat = new Seat();
                seat.setSeatRoom(room);
                seat.setRowLabel(rowLabel);
                seat.setColumnNumber(col);
                seat.setSeatNumber(rowLabel + col);
                seat.setSeatType(defaultSeatType);
                seat.setStatus(true);
                generatedSeats.add(seat);
            }
        }

        return seatRepository.saveAll(generatedSeats);
    }

    @Override
    @Transactional
    public Seat updateSeat(Integer id, Seat seat) {
        Seat existing = getSeatById(id);
        
        existing.setRowLabel(seat.getRowLabel());
        existing.setColumnNumber(seat.getColumnNumber());
        existing.setSeatNumber(seat.getSeatNumber());
        existing.setStatus(seat.getStatus());

        if (seat.getSeatType() != null && seat.getSeatType().getId() != null) {
            SeatType seatType = seatTypeRepository.findById(seat.getSeatType().getId())
                    .orElseThrow(() -> new SeatNotFoundException("Seat type not found"));
            existing.setSeatType(seatType);
        }

        return seatRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteSeat(Integer id) {
        Seat existing = getSeatById(id);
        existing.setStatus(false);
        seatRepository.save(existing);
    }

    @Override
    @Transactional
    public List<Seat> arrangeSeats(Integer roomId, List<Seat> seats) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        // Delete existing seats
        List<Seat> existingSeats = seatRepository.findBySeatRoomId(roomId);
        seatRepository.deleteAll(existingSeats);

        // Save new arrangement
        for (Seat seat : seats) {
            seat.setSeatRoom(room);
            seat.setStatus(true);
        }
        return seatRepository.saveAll(seats);
    }
}