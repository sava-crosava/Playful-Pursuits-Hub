package com.sava.playful.pursuits.hub.playfulpursuitshub.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userName;
    private String password;
    private String roles;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirthday;

    @OneToOne(mappedBy = "myUser", cascade = CascadeType.ALL)
    private Channel channel;
}
