package com.sava.playful.pursuits.hub.playfulpursuitshub.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

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
    private String logoImageName;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfCreation;

    @OneToOne
    @JoinColumn(name = "authorId", referencedColumnName = "id")
    private MyUser myUser;

    @OneToMany(mappedBy = "channel")
    private List<Post> posts;
}
