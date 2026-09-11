package cinema_backend.api_movie_system.service.impl;

import cinema_backend.api_movie_system.service.BranchService;
import cinema_backend.api_movie_system.repository.*;
import cinema_backend.api_movie_system.exception.BranchNotFoundException;
import cinema_backend.api_movie_system.models.Branch;
import cinema_backend.api_movie_system.models.City;
import cinema_backend.api_movie_system.models.State;
import cinema_backend.api_movie_system.models.Country;
import cinema_backend.api_movie_system.models.Movie;

import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceimpl implements BranchService {

    private final BranchRepository branchRepository;

    public BranchServiceimpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public String createBranch(Branch branch) {
        branch.prepareForCreate();

        if (branch.getId() == null) {
            branch.setId(generateNextBranchId());
        }

        branchRepository.save(branch);
        return "Success";
    }

    @Override
    public String updateBranch(Branch branch) {
        Branch existingBranch = branchRepository.findById(branch.getId())
                .orElseThrow(() -> new BranchNotFoundException("Branch not found with ID: " + branch.getId()));

        if (branch.getIsDeleted() == null) {
            branch.setIsDeleted(existingBranch.getIsDeleted());
        }

        if (branch.getStatus() == null) {
            branch.setStatus(existingBranch.getStatus());
        }

        if (branch.getCreatedBy() == null) {
            branch.setCreatedBy(existingBranch.getCreatedBy());
        }

        branchRepository.save(branch);
        return "Success";
    }

    @Override
    public String deleteBranch(int branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new BranchNotFoundException("Branch not found with ID: " + branchId));
        branch.setIsDeleted(true);
        branchRepository.save(branch);
        return "Success";
    }

    @Override
    public Branch getBranch(int branchId) {
        return branchRepository.findById(branchId)
                .orElseThrow(() -> new BranchNotFoundException("Branch not found with ID: " + branchId));
    }

     @Override
    public List<Branch> getAllBranches() {
        return branchRepository.findVisible();
    }


    @Override
    public Page<Branch> getBranchPage(String search, String status, Pageable pageable) {
        Boolean statusFilter = normalizeStatusFilter(status);

        if (search != null && !search.isBlank() && statusFilter != null) {
            return branchRepository.findVisibleByNameContainingIgnoreCaseAndStatus(search, statusFilter, pageable);
        }

        if (search != null && !search.isBlank()) {
            return branchRepository.findVisibleByNameContainingIgnoreCase(search, pageable);
        }

        if (statusFilter != null) {
            return branchRepository.findVisibleByStatus(statusFilter, pageable);
        }

        return branchRepository.findVisible(pageable);
    }

    private Boolean normalizeStatusFilter(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }

        String normalizedStatus = status.trim().toUpperCase();

        if ("ACTIVE".equals(normalizedStatus)) {
            return true;
        }
        if ("INACTIVE".equals(normalizedStatus)) {
            return false;
        }
        return null;
    }

    private int generateNextBranchId() {
        return branchRepository.findAll().stream()
                .map(Branch::getId)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0) + 1;

    }

}
