package com.sava.playful.pursuits.hub.playfulpursuitshub.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Title cannot be null")
    private String title;
    @NotNull(message = "Descriptions cannot be null")
    private String descriptions;
    private String videoName;
    private String imageName;//todo change on logoImageName

    @Builder.Default
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long views = 0L;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long likes = 0L;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long dislikes = 0L;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfPublication;

    @ElementCollection(targetClass = Category.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "post_categories", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "category")
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonIgnoreProperties("posts")
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "chanel_id", referencedColumnName = "id")
    private Channel channel;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
