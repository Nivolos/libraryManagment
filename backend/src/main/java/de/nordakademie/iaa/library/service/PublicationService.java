package de.nordakademie.iaa.library.service;

import de.nordakademie.iaa.library.model.Publication;

import java.util.List;
import java.util.Set;

public interface PublicationService {

    List<Publication> listPublications(String query, Long typeId, Set<Long> keywordIds);

    Publication loadPublication(long publicationId);

    Publication savePublication(Publication publication);

    Publication updatePublication(long publicationId, Publication publication);

    void deletePublication(long publicationId);
}
