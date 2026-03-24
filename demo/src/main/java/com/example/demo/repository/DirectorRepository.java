package com.example.demo.repository;

import com.example.demo.entity.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<Director , Integer> {

    @Query(value = "SELECT * FROM directors WHERE LOWER(name) LIKE LOWER(CONCAT('%':name '%'))", nativeQuery = true)
    List<Director> findByName(String name);

    @Query(value = "SELECT * FROM directors WHERE LOWER(sur_name) LIKE LOWER(CONCAT('%':sur_name '%'))", nativeQuery = true)
    List<Director> findBySurname(String surname);

    @Query(value = "SELECT * FROM directors WHERE LOWER(birthday) LIKE LOWER(CONCAT('%':birthday '%'))", nativeQuery = true)
    List<Director> findByBirthday(String birthday);

    List<Director> findByNameContainingIgnoreCase(String name);
    List<Director> findBySurnameContainingIgnoreCase(String surname);

    Page<Director> findAll(Pageable pageable);
    Page<Director> findByNameContainingIgnoreCaseAndFindBySurnameContainingIgnoreCase(String name, String surname , Pageable pageable);
}
