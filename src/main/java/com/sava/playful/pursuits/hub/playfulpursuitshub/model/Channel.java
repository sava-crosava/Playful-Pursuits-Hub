package com.sava.playful.pursuits.hub.playfulpursuitshub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(min = 5, max = 64, message = "Channel name should be between 5 and 64 characters")
    private String channelName;

    @Size(min = 5, max = 256, message = "Channel Description should be between 5 and 256 characters")
    private String channelDescription;

    private String iconImageName;

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
    @JsonIgnore
    private List<Post> posts;
}
