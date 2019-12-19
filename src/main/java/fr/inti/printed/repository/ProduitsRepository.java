package fr.inti.printed.repository;
import fr.inti.printed.domain.Produits;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Produits entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitsRepository extends MongoRepository<Produits, String> {

}
