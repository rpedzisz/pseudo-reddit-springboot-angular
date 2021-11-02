package com.example.pseudoreddit.services;


import com.example.pseudoreddit.classes.SubredditEnc;
import com.example.pseudoreddit.models.Subforum;
import com.example.pseudoreddit.repository.SubforumRepository;
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


    private final SubforumRepository subforumRepository;

    @Transactional
    public SubredditEnc save(SubredditEnc subredditEnc){

        Subforum subforum = mapSubredditEnc(subredditEnc);
        Subforum save = subforumRepository.save(subforum);
        subredditEnc.setId(save.getSubforumId());
        return subredditEnc;
    }

    @Transactional
    public List<SubredditEnc> getAll(){
        return subforumRepository.findAll()
                .stream()
                .map(this::mapToEnc)
                .collect(toList());



    }

    private SubredditEnc mapToEnc(Subforum subforum){


        return SubredditEnc.builder().subredditName(subforum.getName())
                .id(subforum.getSubforumId())
                .numberOfPosts(subforum.getPosts().size())
                .build();
    }

    private Subforum mapSubredditEnc(SubredditEnc subredditEnc) {
        return Subforum.builder().name(subredditEnc.getSubredditName()).description(subredditEnc.getDescription()).build();

    }



}
