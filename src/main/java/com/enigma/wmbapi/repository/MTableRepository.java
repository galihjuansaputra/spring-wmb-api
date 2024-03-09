package com.enigma.wmbapi.repository;

import com.enigma.wmbapi.entity.MTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MTableRepository extends JpaRepository<MTable, String> {
}
