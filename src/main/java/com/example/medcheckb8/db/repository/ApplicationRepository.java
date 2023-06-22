package com.example.medcheckb8.db.repository;

import com.example.medcheckb8.db.dto.response.ApplicationResponse;
import com.example.medcheckb8.db.entities.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("select new com.example.medcheckb8.db.dto.response.ApplicationResponse(a.id,a.name,a.date,a.phoneNumber,a.processed)from Application a")
    List<ApplicationResponse> getAllApplication();

    @Query("select new com.example.medcheckb8.db.dto.response.ApplicationResponse(a.id,a.name,a.date,a.phoneNumber,a.processed) " +
            "from  Application a  where a.name  ilike  concat('%' ,:word, '%') ")
    List<ApplicationResponse> globalSearch(String word);

    @Query("select new com.example.medcheckb8.db.dto.response.ApplicationResponse(a.id,a.name,a.date,a.phoneNumber,a.processed)from Application a where a.id = ?1")
    Optional<ApplicationResponse> findByIdApplication(Long id);

    @Modifying
    @Query("DELETE FROM Application a WHERE a.id IN (:ids)")
    void deleteApplications(@Param("ids") List<Long> ids);

    @Query("select new com.example.medcheckb8.db.dto.response.ApplicationResponse(a.id,a.name,a.date," +
                    "a.phoneNumber,a.processed)" +
                    " from Application a order by a.id desc ")
    Page<ApplicationResponse> findAllBy(Pageable pageable);

}