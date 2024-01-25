package com.sava.playful.pursuits.hub.playfulpursuitshub.content.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title, descriptions, fileName;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long views = 0L;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long likes = 0L;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long dislikes = 0L;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "post_category",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties("posts")
    private Set<Category> categories = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
