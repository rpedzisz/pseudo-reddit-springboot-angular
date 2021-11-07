package com.example.pseudoreddit.services;


import com.example.pseudoreddit.classes.VoteDto;
import com.example.pseudoreddit.exceptions.RedditException;
import com.example.pseudoreddit.models.Post;
import com.example.pseudoreddit.models.Vote;
import com.example.pseudoreddit.repository.PostRepository;
import com.example.pseudoreddit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.pseudoreddit.models.VoteType.UPVOTE;
import static com.example.pseudoreddit.models.VoteType.DOWNVOTE;

@Service
@AllArgsConstructor
public class VoteService {
        private final VoteRepository voteRepository;
        private final PostRepository postRepository;
        private final AuthService authService;


    public void vote(VoteDto voteDto){
    Post post = postRepository.findById(voteDto.getPostId())
            .orElseThrow(()-> new RedditException("Post not found"));

    Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if ( voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new RedditException("Already up/downvoted this.");

        }

        if(UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        }
        else {
            post.setVoteCount(post.getVoteCount() - 1);
        }


        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);


    }
    private Vote mapToVote(VoteDto voteDto, Post post){
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();

    }




}
