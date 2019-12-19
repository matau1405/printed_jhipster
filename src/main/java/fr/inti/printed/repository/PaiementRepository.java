package fr.inti.printed.repository;
import fr.inti.printed.domain.Paiement;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Paiement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaiementRepository extends MongoRepository<Paiement, String> {

}
