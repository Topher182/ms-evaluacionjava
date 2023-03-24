package com.msevaluacionjava.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "PHONE")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class Phone {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String number;
    private String cityCode;
    private String countryCode;
    @JsonBackReference
    @ManyToOne()
    @ToString.Exclude
    private User user;
}