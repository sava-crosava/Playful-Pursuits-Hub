package com.sava.playful.pursuits.hub.playfulpursuitshub.model;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne
    @JoinColumn(name = "authorId", referencedColumnName = "id")
    private MyUser myUser;

    private String channelDescription;
    private String password;
    private String roles;
}
