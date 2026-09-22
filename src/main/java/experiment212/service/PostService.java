package experiment212.service;

import experiment212.exception.ResourceNotFoundException;
import experiment212.model.Post;
import experiment212.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        Post post = postRepository.findById(id);

        if (post == null) {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }

        return post;
    }

    public Post updatePost(Long id, Post updatedPost) {
        Post existingPost = getPostById(id);

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());

        return postRepository.save(existingPost);
    }

    public void deletePost(Long id) {
        getPostById(id);
        postRepository.deleteById(id);
    }
}