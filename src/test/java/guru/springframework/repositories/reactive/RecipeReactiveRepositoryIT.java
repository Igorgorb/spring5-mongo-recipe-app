package guru.springframework.repositories.reactive;

import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 *
 * @author igorg
 */
@DataMongoTest
public class RecipeReactiveRepositoryIT {

  private static final String SPICY__GRILLED__CHICKEN__TACO = "Spicy Grilled Chicken Taco";
  
  @Autowired
  RecipeReactiveRepository repository;

  Recipe tacosRecipe;

  @BeforeEach
  public void setUp() {
    tacosRecipe = new Recipe();
    tacosRecipe.setDescription(SPICY__GRILLED__CHICKEN__TACO);
    tacosRecipe.setCookTime(9);
    tacosRecipe.setPrepTime(20);
    tacosRecipe.setDifficulty(Difficulty.MODERATE);

  }

  @Test
  public void testSaveAndCheckSaved() {
    Recipe savedRecipe = repository.save(tacosRecipe).block();

    Mono<Recipe> recipeMono = repository.findById(savedRecipe.getId());

    StepVerifier
      .create(recipeMono)
      .assertNext(recipe -> {
        assertEquals(SPICY__GRILLED__CHICKEN__TACO, recipe.getDescription());
        assertNotNull(recipe.getId());
      })
      .expectComplete()
      .verify();
  }

  @Test
  public void testUpdate() {
    Recipe recipeSaved = repository.save(tacosRecipe).block();

    recipeSaved.setDescription("Very "+SPICY__GRILLED__CHICKEN__TACO);
    repository.save(recipeSaved).block();

    Recipe recipeUpd = repository.findById(recipeSaved.getId()).block();

    assertEquals("Very "+SPICY__GRILLED__CHICKEN__TACO, recipeUpd.getDescription());
    assertEquals(recipeSaved.getId(), recipeUpd.getId());
  }

//  @Test
//  public void testDelete() {
//    Recipe recipeSaved = repository.save(tacosRecipe).block();
//
//    Mono<Void> mono = repository.deleteById(recipeSaved.getId());
//
//    Mono<Recipe> recipeMono = repository.findById(recipeSaved.getId());
//    StepVerifier
//      .create(recipeMono)
//      .assertNext(recipe -> {
//        //assertEquals("Teaspoon1", oum.getDescription());
//        assertNull(recipe);
//      })
//      .expectComplete()
//      .verify();
//  }
}
