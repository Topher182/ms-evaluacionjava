package com.msevaluacionjava.entity;

import com.msevaluacionjava.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "USER")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String name;
    private String email;
    private String password;
    @JsonFormat(pattern = Constants.DATE_FORMAT_YYYY_MM_DD)
    private Date createAt = new Date();
    @JsonFormat(pattern = Constants.DATE_FORMAT_YYYY_MM_DD)
    private Date modified = new Date();
    @JsonFormat(pattern = Constants.DATE_FORMAT_YYYY_MM_DD)
    private Date lastLogin = new Date();
    private boolean isActive = true;
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Phone> phones;
}