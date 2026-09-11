package cinema_backend.api_movie_system.repository;

import cinema_backend.api_movie_system.models.Branch;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    @EntityGraph(attributePaths = { "city", "city.state", "city.state.country" })
    @Query("""
        SELECT b FROM Branch b
        WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))
          AND b.status = :status
          AND (b.isDeleted = false OR b.isDeleted IS NULL)
        """)
    Page<Branch> findVisibleByNameContainingIgnoreCaseAndStatus(
        @Param("name") String name,
        @Param("status") Boolean status,
        Pageable pageable);

        @EntityGraph(attributePaths = { "city", "city.state", "city.state.country" })
    @Query("""
        SELECT b FROM Branch b
        WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))
          AND (b.isDeleted = false OR b.isDeleted IS NULL)
        """)
    Page<Branch> findVisibleByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

        @EntityGraph(attributePaths = { "city", "city.state", "city.state.country" })
    @Query("""
        SELECT b FROM Branch b
        WHERE b.status = :status
          AND (b.isDeleted = false OR b.isDeleted IS NULL)
        """)
    Page<Branch> findVisibleByStatus(@Param("status") Boolean status, Pageable pageable);

        @EntityGraph(attributePaths = { "city", "city.state", "city.state.country" })
    @Query("""
        SELECT b FROM Branch b
        WHERE b.isDeleted = false OR b.isDeleted IS NULL
        """)
    Page<Branch> findVisible(Pageable pageable);

        @EntityGraph(attributePaths = { "city", "city.state", "city.state.country" })
    @Query("""
        SELECT b FROM Branch b
        WHERE b.isDeleted = false OR b.isDeleted IS NULL
        """)
    List<Branch> findVisible();
}



