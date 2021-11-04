package com.example.pseudoreddit.services;

import com.example.pseudoreddit.Mapper.CommentMapper;
import com.example.pseudoreddit.classes.CommentDto;
import com.example.pseudoreddit.exceptions.RedditException;
import com.example.pseudoreddit.models.Comment;
import com.example.pseudoreddit.models.NotificationEmail;
import com.example.pseudoreddit.models.Post;
import com.example.pseudoreddit.models.User;
import com.example.pseudoreddit.repository.CommentRepository;
import com.example.pseudoreddit.repository.PostRepository;
import com.example.pseudoreddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final EmailContentService emailContentService;
    private final EmailSenderService emailSenderService;


    public void save(CommentDto commentDto){
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new RedditException("Post doesnt exist"));
        User userName =  authService.getCurrentUser();
    Comment comment = commentMapper.map(commentDto, post, authService.getCurrentUser());

    commentRepository.save(comment);

    String message = emailContentService.buildEmailContent(post.getUser().getUsername() + " posted a comment on post: " + POST_URL);
    sendCommentNotification(message, post.getUser() );
    }

    private void sendCommentNotification(String message, User user){
        emailSenderService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post ", user.getEmail(), message));
    }

    public List<CommentDto> getAllCommentsForPost(Long postId){
       Post post =  postRepository.findById(postId).orElseThrow(() -> new RedditException("Post not found"));
       return commentRepository.findByPost(post)
               .stream()
               .map(commentMapper::mapToDto).collect(Collectors.toList());


    }


    public Object getAllCommentsForUser(String userName) {
        User user =  userRepository.findByUsername(userName).orElseThrow(() -> new RedditException("Post not found"));


        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto).collect(Collectors.toList());


    }
}
