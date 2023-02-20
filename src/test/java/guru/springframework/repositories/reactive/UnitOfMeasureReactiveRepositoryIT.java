package guru.springframework.repositories.reactive;

import guru.springframework.domain.UnitOfMeasure;
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
public class UnitOfMeasureReactiveRepositoryIT {

  private static final String TEASPOON1 = "Teaspoon1";
  
  @Autowired
  UnitOfMeasureReactiveRepository repository;

  @BeforeEach
  public void setUp() {
  }

  @Test
  public void testSaveAndCheckSaved() {
    UnitOfMeasure uom1 = new UnitOfMeasure();
    uom1.setDescription(TEASPOON1);
    repository.save(uom1).block();

    Mono<UnitOfMeasure> uomMono = repository.findByDescription(TEASPOON1);

    StepVerifier
      .create(uomMono)
      .assertNext(oum -> {
        assertEquals(TEASPOON1, oum.getDescription());
        assertNotNull(oum.getId());
      })
      .expectComplete()
      .verify();
  }

  @Test
  public void testUpdate() {
    UnitOfMeasure uom1 = new UnitOfMeasure();
    uom1.setDescription(TEASPOON1);
    UnitOfMeasure uomSaved = repository.save(uom1).block();
    
    uomSaved.setDescription("New Teaspoon");
    repository.save(uomSaved).block();
    
    UnitOfMeasure uomUpd = repository.findById(uomSaved.getId()).block();
    
    assertEquals("New Teaspoon", uomUpd.getDescription());
    assertEquals(uomSaved.getId(), uomUpd.getId());
  }

//  @Test
//  public void testDelete() {
//    UnitOfMeasure uom1 = new UnitOfMeasure();
//    uom1.setDescription(TEASPOON1);
//    UnitOfMeasure uomSaved = repository.save(uom1).block();
//        
//    Mono<Void> mono = repository.deleteById(uomSaved.getId());    
//    
//    Mono<UnitOfMeasure> uomMono = repository.findById(uomSaved.getId());
//    StepVerifier
//      .create(uomMono)
//      .assertNext(oum -> {
//        //assertEquals("Teaspoon1", oum.getDescription());
//        assertNull(oum);
//      })
//      .expectComplete()
//      .verify();
//  }
}
