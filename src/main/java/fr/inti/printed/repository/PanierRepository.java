package fr.inti.printed.repository;
import fr.inti.printed.domain.Panier;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Panier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PanierRepository extends MongoRepository<Panier, String> {

}
