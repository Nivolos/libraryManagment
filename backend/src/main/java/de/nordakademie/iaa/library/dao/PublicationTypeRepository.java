package de.nordakademie.iaa.library.dao;

import de.nordakademie.iaa.library.model.PublicationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublicationTypeRepository extends JpaRepository<PublicationType, Long> {

    Optional<PublicationType> findByNameIgnoreCase(String name);
}
