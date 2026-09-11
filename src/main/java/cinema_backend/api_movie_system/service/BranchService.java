package cinema_backend.api_movie_system.service;

import cinema_backend.api_movie_system.models.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BranchService {
    String createBranch(Branch branch);

    String updateBranch(Branch branch);

    String deleteBranch(int branchId);

    Branch getBranch(int branchId);

    List<Branch> getAllBranches();

    Page<Branch> getBranchPage(String search,String status,Pageable pageable);


}
