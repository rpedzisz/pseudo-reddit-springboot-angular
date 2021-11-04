package com.example.pseudoreddit.Mapper;

import com.example.pseudoreddit.classes.PostRequest;
import com.example.pseudoreddit.classes.PostResponse;
import com.example.pseudoreddit.models.Post;
import com.example.pseudoreddit.models.Subreddit;
import com.example.pseudoreddit.models.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PostMapper {



    @Mapping(target = "creationDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostResponse mapToDto(Post post);

}
