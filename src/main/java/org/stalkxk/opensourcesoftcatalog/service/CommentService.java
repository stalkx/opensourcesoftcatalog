package org.stalkxk.opensourcesoftcatalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stalkxk.opensourcesoftcatalog.entity.Comment;
import org.stalkxk.opensourcesoftcatalog.entity.Program;
import org.stalkxk.opensourcesoftcatalog.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProgramService programService;

    public Comment saveComment(Comment comment){
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Page<Comment> findAllByProgram(Integer programId, Pageable pageable){
        Program program = programService.findById(programId).orElseThrow();
        return commentRepository.findAllByProgram(program, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Comment> findById(Integer commentId){
        return commentRepository.findById(commentId);
    }

    public Double calculateAverageRating(Integer programId, Pageable pageable) {
        Program program = programService.findById(programId).orElseThrow();
        Page<Comment> comments = commentRepository.findAllByProgram(program, pageable);
        return comments.stream()
                .mapToDouble(Comment::getRating)
                .average()
                .orElse(0.0);
    }

    @Transactional(readOnly = true)
    public Page<Comment> findAllComment(Pageable pageable){
        return commentRepository.findAll(pageable);
    }

    public Comment updateComment(Comment updatedComment){
        Comment comment = findById(updatedComment.getCommentId()).orElseThrow();
        comment.setCommentBody(updatedComment.getCommentBody());
        comment.setCommentId(updatedComment.getCommentId());
        comment.setProgram(updatedComment.getProgram());
        comment.setUser(updatedComment.getUser());
        return commentRepository.save(comment);
    }

    public void removeComment(Comment comment){
        commentRepository.delete(comment);
    }
}
