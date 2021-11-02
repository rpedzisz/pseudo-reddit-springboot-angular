package com.example.pseudoreddit.Mapper;

import com.example.pseudoreddit.classes.SubredditEnc;
import com.example.pseudoreddit.models.Subforum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditEnc mapSubredditToEnc(Subforum subreddit);

}
