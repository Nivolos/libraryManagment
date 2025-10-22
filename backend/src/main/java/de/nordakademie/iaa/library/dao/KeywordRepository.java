package de.nordakademie.iaa.library.dao;

import de.nordakademie.iaa.library.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByNameIgnoreCase(String name);
}
