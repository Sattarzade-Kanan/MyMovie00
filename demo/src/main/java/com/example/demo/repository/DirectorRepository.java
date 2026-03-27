package com.example.demo.repository;

import com.example.demo.dto.DirectorDTO;
import com.example.demo.entity.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<Director , Integer> {

    List<Director> findByName(String name);
    List<Director> findBySurname(String surname);

   List<Director> findByNameContainingIgnoreCase(String name);
   List<Director> findBySurnameContainingIgnoreCase(String surname);

    Page<Director> findAll(Pageable pageable);
    Page<Director> paginationNeed(String name, String surname, Pageable pageable);
 }
