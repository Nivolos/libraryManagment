package de.nordakademie.iaa.library.dao;

import de.nordakademie.iaa.library.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
