package com.sava.playful.pursuits.hub.playfulpursuitshub.model;

import jakarta.persistence.*;
import lombok.*;

<<<<<<< HEAD
import java.util.Date;

=======
>>>>>>> origin/add-user-and-channels
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
<<<<<<< HEAD

    @Column(unique = true)
    private String channelName;
    private String channelDescription;
    private Date dateOfCreation;
=======
    @Column(unique = true)
    private String channelName;
>>>>>>> origin/add-user-and-channels

    @OneToOne
    @JoinColumn(name = "authorId", referencedColumnName = "id")
    private MyUser myUser;

<<<<<<< HEAD
=======
    private String channelDescription;
    private String password;
    private String roles;
>>>>>>> origin/add-user-and-channels
}
