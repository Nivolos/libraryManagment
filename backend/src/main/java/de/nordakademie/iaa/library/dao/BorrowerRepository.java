package de.nordakademie.iaa.library.dao;

import de.nordakademie.iaa.library.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {

    Optional<Borrower> findByMatriculation(String matriculation);
}
