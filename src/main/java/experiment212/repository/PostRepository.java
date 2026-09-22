package experiment212.repository;

import experiment212.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {

    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Post save(Post post) {
        if (post.getId() == null) {
            post.setId(idGenerator.getAndIncrement());
        }

        posts.put(post.getId(), post);
        return post;
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

    public Post findById(Long id) {
        return posts.get(id);
    }

    public void deleteById(Long id) {
        posts.remove(id);
    }

    public boolean existsById(Long id) {
        return posts.containsKey(id);
    }
}