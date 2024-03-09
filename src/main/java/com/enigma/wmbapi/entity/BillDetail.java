package com.enigma.wmbapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "t_bill_detail")
@NoArgsConstructor
@AllArgsConstructor
public class BillDetail {
    @Id
    @Column(name = "id", nullable = false, length = Integer.MAX_VALUE)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    @JsonBackReference
    private Bill bill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @NotNull
    @Column(name = "qty", nullable = false)
    private Integer qty;

    @NotNull
    @Column(name = "price", nullable = false, updatable = false)
    private Long price;

}