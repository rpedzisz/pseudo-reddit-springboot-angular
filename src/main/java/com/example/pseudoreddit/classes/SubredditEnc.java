package com.example.pseudoreddit.classes;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditEnc {
    private Long id;
    private String subredditName;
    private String description;
    private Integer numberOfPosts;
}
