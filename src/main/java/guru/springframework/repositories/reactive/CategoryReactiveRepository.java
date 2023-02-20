package guru.springframework.repositories.reactive;

import guru.springframework.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 *
 * @author igorg
 * date 20 лют. 2023 р.
 */
public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String>  {

  Mono<Category> findByDescription(String description);
}
