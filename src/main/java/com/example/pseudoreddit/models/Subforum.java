package com.example.pseudoreddit.models;


import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.websocket.OnError;

import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subforum {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long subforumId;
    @NotBlank(message = "Name can't be blank")
    private String name;
    @NotBlank(message = "Description can't be blank")
    private String description;
    @OneToMany(fetch = LAZY)
    private List<Post> posts;
    private Instant creationDate;
    @ManyToOne(fetch = LAZY)
    private User user;



}
