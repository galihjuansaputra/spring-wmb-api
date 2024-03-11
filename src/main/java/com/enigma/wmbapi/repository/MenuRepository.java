package com.enigma.wmbapi.repository;

import com.enigma.wmbapi.dto.response.MenuResponse;
import com.enigma.wmbapi.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {
    Page<MenuResponse> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
