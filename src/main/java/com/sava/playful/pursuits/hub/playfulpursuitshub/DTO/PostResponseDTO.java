package com.sava.playful.pursuits.hub.playfulpursuitshub.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PostResponseDTO {

    @NotNull(message = "Title cannot be null")
    @Size(min = 5, max = 128, message = "Title should be between 5 and 128 characters")
    private String title;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long views;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long likes;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long dislikes;

    @Size(min = 5, max = 64, message = "Channel name should be between 5 and 64 characters")
    private String channelName;

    private String thumbnailImageUrl;
    private String channelIconImageUrl;
}
