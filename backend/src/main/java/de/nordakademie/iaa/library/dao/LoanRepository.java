package de.nordakademie.iaa.library.dao;

import de.nordakademie.iaa.library.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    long countByPublicationIdAndReturnedAtIsNull(Long publicationId);

    List<Loan> findByReturnedAtIsNullOrderByDueAtAsc();
}
