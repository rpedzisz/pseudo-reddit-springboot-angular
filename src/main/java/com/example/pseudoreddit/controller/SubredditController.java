package com.example.pseudoreddit.controller;


import com.example.pseudoreddit.classes.SubredditEnc;
import com.example.pseudoreddit.services.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;
    @PostMapping
    public ResponseEntity<SubredditEnc> createSubreddit(@RequestBody SubredditEnc subredditEnc){

       return ResponseEntity.status(HttpStatus.CREATED).body(
                subredditService.save(subredditEnc));

    }

    @GetMapping
    public ResponseEntity<List<SubredditEnc>> getAllSubreddits(){
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAll());
    }





}
