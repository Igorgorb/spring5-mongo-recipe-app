package guru.springframework.repositories.reactive;

import guru.springframework.domain.Category;
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
public class CategoryReactiveRepositoryIT {

  private static final String AMERICAN = "American1";

  @Autowired
  CategoryReactiveRepository repository;

  @BeforeEach
  public void setUp() {
  }

  @Test
  public void testSaveAndCheckSaved() {
    Category cat1 = new Category();
    cat1.setDescription(AMERICAN);
    repository.save(cat1).block();

    Mono<Category> catMono = repository.findByDescription(AMERICAN);

    StepVerifier
      .create(catMono)
      .assertNext(cat -> {
        assertEquals(AMERICAN, cat.getDescription());
        assertNotNull(cat.getId());
      })
      .expectComplete()
      .verify();
  }

  @Test
  public void testUpdate() {
    Category cat1 = new Category();
    cat1.setDescription(AMERICAN);
    Category catSaved = repository.save(cat1).block();

    catSaved.setDescription("New American1");
    repository.save(catSaved).block();

    Category catUpd = repository.findById(catSaved.getId()).block();

    assertEquals("New American1", catUpd.getDescription());
    assertEquals(catSaved.getId(), catUpd.getId());
  }

//  @Test
//  public void testDelete() {
//    Category cat1 = new Category();
//    cat1.setDescription(AMERICAN);
//    Category catSaved = repository.save(cat1).block();
//        
//    Mono<Void> mono = repository.deleteById(catSaved.getId());    
//    
//    Mono<Category> catMono = repository.findById(catSaved.getId());
//    StepVerifier
//      .create(catMono)
//      .assertNext(cat -> {
//        //assertEquals("Teaspoon1", oum.getDescription());
//        assertNull(cat);
//      })
//      .expectComplete()
//      .verify();
//  }
}
