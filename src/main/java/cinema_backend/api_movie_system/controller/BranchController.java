package cinema_backend.api_movie_system.controller;

import cinema_backend.api_movie_system.models.Branch;
import cinema_backend.api_movie_system.models.User;
import cinema_backend.api_movie_system.response.ResponseHandler;
import cinema_backend.api_movie_system.service.BranchService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/branch")

public class BranchController {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("id", "name", "status", "createdDate");

    private final BranchService branchService;

    public BranchController(BranchService branchService){
        this.branchService = branchService;
    }

    @PreAuthorize("hasAuthority('PERM_BRANCH_READ')")
    @GetMapping("{branchId}")
    public ResponseEntity<Object> getBranchDetails(@PathVariable("branchId") int branchId){
        Branch branch = branchService.getBranch(branchId);
        return ResponseHandler.responseBuilder("Request completed successfully", HttpStatus.OK, branch);
    }

    @PreAuthorize("hasAuthority('PERM_BRANCH_READ')")
    @GetMapping()
    public List<Branch> getAllBranchDetails(){
        return branchService.getAllBranches();
    }

    @PreAuthorize("hasAuthority('PERM_BRANCH_CREATE')")
    @PostMapping
    public String createBranchDetails(@RequestBody Branch branch, @AuthenticationPrincipal User user){
        branch.setCreatedBy(user.getId());
        branchService.createBranch(branch);
        return "Success";
    }

    @PreAuthorize("hasAuthority('PERM_BRANCH_READ')")
    @GetMapping("/page")
    public Page<Branch> getBranchPage(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdDate") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "DESC") String sortOrder) {

        String normalizedSortBy = normalizeSortField(sortBy);
        Sort.Direction direction = Sort.Direction.fromOptionalString(sortOrder).orElse(Sort.Direction.DESC);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, normalizedSortBy));
        return branchService.getBranchPage(search, status, pageable);
    }

    @PreAuthorize("hasAuthority('PERM_BRANCH_UPDATE')")
    @PutMapping
    public String updateBranchDetails(@RequestBody Branch branch){
        branchService.updateBranch(branch);
        return "Success";
    }

    @PreAuthorize("hasAuthority('PERM_BRANCH_DELETE')")
    @DeleteMapping("{branchId}")
    public String deleteBranchDetails(@PathVariable("branchId") int branchId){
        branchService.deleteBranch(branchId);
        return "Success";
    }

    private String normalizeSortField(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return "createdDate";
        }

        if ("createDate".equals(sortBy)) {
            return "createdDate";
        }

        return ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "createdDate";
    }
}
