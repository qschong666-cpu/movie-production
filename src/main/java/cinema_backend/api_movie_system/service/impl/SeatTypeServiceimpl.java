package cinema_backend.api_movie_system.service.impl;

import cinema_backend.api_movie_system.exception.MovieNotFoundException;
import cinema_backend.api_movie_system.exception.SeatNotFoundException;
import cinema_backend.api_movie_system.models.Movie;
import cinema_backend.api_movie_system.models.SeatType;
import cinema_backend.api_movie_system.repository.SeatTypeRepository;
import cinema_backend.api_movie_system.service.SeatTypeService;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SeatTypeServiceimpl implements SeatTypeService {

    private final SeatTypeRepository seatTypeRepository;

    public SeatTypeServiceimpl(SeatTypeRepository seatTypeRespository) {
        this.seatTypeRepository = seatTypeRespository;
    }

    @Override
    public SeatType createSeatType(SeatType seatType) {
        seatType.prepareForCreate();

        if (seatType.getId() == null) {
            seatType.setId(generateNextSeatTypeId());
        }

        return seatTypeRepository.save(seatType);
    }

    @Override
    public SeatType updateSeatType(SeatType seatType) {
        SeatType existingSeatType = seatTypeRepository.findById(seatType.getId())
                .orElseThrow(() -> new SeatNotFoundException("SeatType not found with id " + seatType.getId()));

        return seatTypeRepository.save(seatType);
    }

    @Override
    public String deletedSeatType(int seatTypeId) {
        return softDeletedSeatType(seatTypeId);
    }

    @Override
    public String softDeletedSeatType(int seatTypeId) {
        SeatType seatType = seatTypeRepository.findById(seatTypeId)
                .orElseThrow(() -> new SeatNotFoundException("Seat Type not found with id " + seatTypeId));
        seatType.setIsDeleted(true);
        seatTypeRepository.save(seatType);
        return "Success";
    }

    @Override
    public SeatType getSeatType(int seatTypeId) {
        SeatType seatType = seatTypeRepository.findById(seatTypeId)
                .orElseThrow(() -> new SeatNotFoundException("SeatType not found with id " + seatTypeId));

        return seatType;
    }

    @Override
    public List<SeatType> getAllSeatTypes() {
        return seatTypeRepository.findByIsDeletedFalse();
    }

    @Override
    public Page<SeatType> getSeatTypesPages(String search, String status, Pageable pageable) {

        if (search != null && !search.isBlank()) {
            return seatTypeRepository.findByNameContainingIgnoreCaseAndIsDeletedFalse(search, pageable);
        }
        
        return seatTypeRepository.findByIsDeletedFalse(pageable);
    }



    private int generateNextSeatTypeId() {
        return seatTypeRepository.findByIsDeletedFalse().stream()
                .map(SeatType::getId)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0) + 1;
    }
}