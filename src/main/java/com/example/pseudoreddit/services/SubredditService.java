package com.example.pseudoreddit.services;


import com.example.pseudoreddit.Mapper.SubredditMapper;
import com.example.pseudoreddit.classes.SubredditDto;

import com.example.pseudoreddit.exceptions.RedditException;
import com.example.pseudoreddit.models.Subreddit;
import com.example.pseudoreddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {


    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditEnc){

        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditEnc));
        subredditEnc.setId(save.getId());
        return subredditEnc;
    }

    @Transactional
    public List<SubredditDto> getAll(){
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());



    }


    public SubredditDto getSubreddit(Long id){
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new RedditException("No subreddit with this id exists"));
        return  subredditMapper.mapSubredditToDto(subreddit);


    }




    private SubredditDto mapToDto(Subreddit subforum){


        return SubredditDto.builder().name(subforum.getName())
                .id(subforum.getId())
                .numberOfPosts(subforum.getPosts().size())
                .build();
    }

    private Subreddit mapSubredditEnc(SubredditDto subredditEnc) {
        return Subreddit.builder().name(subredditEnc.getName()).description(subredditEnc.getDescription()).build();

    }



}
