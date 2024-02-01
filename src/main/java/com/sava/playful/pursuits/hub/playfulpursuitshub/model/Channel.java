package com.sava.playful.pursuits.hub.playfulpursuitshub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "channels")
    @JsonIgnoreProperties("channels")
    private Set<MyUser> users = new HashSet<>();

    @OneToMany(mappedBy = "channel")
    private List<Post> posts;
}
