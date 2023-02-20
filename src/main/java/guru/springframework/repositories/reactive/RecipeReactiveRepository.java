package guru.springframework.repositories.reactive;

import guru.springframework.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 *
 * @author igorg
 * date 20 лют. 2023 р.
 */
public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String>  {

}
