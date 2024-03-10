package com.enigma.wmbapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "m_menu")
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @Column(name = "id", nullable = false, length = Integer.MAX_VALUE)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "price")
    private Long price;

}