package com.sava.playful.pursuits.hub.playfulpursuitshub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String channelName;
    private String channelDescription;
    private Date dateOfCreation;

    @OneToOne
    @JoinColumn(name = "authorId", referencedColumnName = "id")
    private MyUser myUser;

}
