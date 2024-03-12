package com.enigma.wmbapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "token")
    private String token;

    @Column(name = "redirect_url")
    private String redirectUrl;

    // ordered, pending, settlement, cancel, deny, expire, failure
    @Column(name = "bill_status")
    private String billStatus;

    @OneToOne(mappedBy = "payment")
    private Bill bill;
}
