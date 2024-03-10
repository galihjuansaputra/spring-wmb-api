package com.enigma.wmbapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "m_table")
@NoArgsConstructor
@AllArgsConstructor
public class MTable {
    @Id
    @Column(name = "id", nullable = false, length = Integer.MAX_VALUE)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

}