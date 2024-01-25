package com.sava.playful.pursuits.hub.playfulpursuitshub.content.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameCategory;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnoreProperties("categories")
    private Set<Post> posts = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
