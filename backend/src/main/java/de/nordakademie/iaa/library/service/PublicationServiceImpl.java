package de.nordakademie.iaa.library.service;

import de.nordakademie.iaa.library.dao.KeywordRepository;
import de.nordakademie.iaa.library.dao.PublicationRepository;
import de.nordakademie.iaa.library.dao.PublicationTypeRepository;
import de.nordakademie.iaa.library.model.Keyword;
import de.nordakademie.iaa.library.model.Publication;
import de.nordakademie.iaa.library.model.PublicationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PublicationServiceImpl implements PublicationService {

    private static final Logger LOG = LoggerFactory.getLogger(PublicationServiceImpl.class);

    private final PublicationRepository publicationRepository;
    private final PublicationTypeRepository publicationTypeRepository;
    private final KeywordRepository keywordRepository;

    public PublicationServiceImpl(PublicationRepository publicationRepository,
                                  PublicationTypeRepository publicationTypeRepository,
                                  KeywordRepository keywordRepository) {
        this.publicationRepository = publicationRepository;
        this.publicationTypeRepository = publicationTypeRepository;
        this.keywordRepository = keywordRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Publication> listPublications(String query, Long typeId, Set<Long> keywordIds) {
        Set<Long> effectiveKeywordIds = keywordIds == null ? Set.of() : keywordIds;
        String effectiveQuery = query == null ? "" : query.trim().toLowerCase();
        return publicationRepository.findAll().stream()
            .filter(publication -> matches(publication, effectiveQuery, typeId, effectiveKeywordIds))
            .sorted(Comparator.comparing(Publication::getTitle, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());
    }

    private boolean matches(Publication publication, String query, Long typeId, Set<Long> keywordIds) {
        boolean matchesQuery = query.isEmpty()
            || containsIgnoreCase(publication.getTitle(), query)
            || containsIgnoreCase(publication.getAuthors(), query)
            || containsIgnoreCase(publication.getPublisher(), query)
            || containsIgnoreCase(publication.getIsbn(), query);

        boolean matchesType = typeId == null
            || (publication.getType() != null && typeId.equals(publication.getType().getId()));

        boolean matchesKeywords = keywordIds.isEmpty()
            || (!publication.getKeywords().isEmpty() && publication.getKeywords().stream()
            .map(Keyword::getId)
            .collect(Collectors.toSet())
            .containsAll(keywordIds));

        return matchesQuery && matchesType && matchesKeywords;
    }

    private boolean containsIgnoreCase(String candidate, String search) {
        if (candidate == null || search.isEmpty()) {
            return search.isEmpty();
        }
        return candidate.toLowerCase().contains(search);
    }

    @Override
    @Transactional(readOnly = true)
    public Publication loadPublication(long publicationId) {
        return publicationRepository.findById(publicationId)
            .orElseThrow(() -> new ResourceNotFoundException("Publication not found"));
    }

    @Override
    public Publication savePublication(Publication publication) {
        LOG.debug("Saving new publication '{}', resolving master data", publication.getTitle());
        Publication toPersist = attachMasterData(new Publication(), publication);
        Publication persisted = publicationRepository.save(toPersist);
        LOG.info("Saved publication '{}' with id {}", persisted.getTitle(), persisted.getId());
        return persisted;
    }

    @Override
    public Publication updatePublication(long publicationId, Publication publication) {
        LOG.debug("Updating publication {} with new data", publicationId);
        Publication existing = loadPublication(publicationId);
        Publication merged = attachMasterData(existing, publication);
        merged.setId(publicationId);
        Publication persisted = publicationRepository.save(merged);
        LOG.info("Updated publication '{}' with id {}", persisted.getTitle(), persisted.getId());
        return persisted;
    }

    @Override
    public void deletePublication(long publicationId) {
        if (!publicationRepository.existsById(publicationId)) {
            throw new ResourceNotFoundException("Publication not found");
        }
        publicationRepository.deleteById(publicationId);
    }

    private Publication attachMasterData(Publication target, Publication source) {
        Long typeId = source.getType() == null ? null : source.getType().getId();
        int keywordCount = source.getKeywords() == null ? 0 : source.getKeywords().size();
        LOG.debug("Attaching master data for publication '{}': typeId={}, keywordCount={}",
            source.getTitle(), typeId, keywordCount);
        target.setTitle(source.getTitle());
        target.setAuthors(source.getAuthors());
        target.setPublishedAt(source.getPublishedAt());
        target.setPublisher(source.getPublisher());
        target.setIsbn(source.getIsbn());
        target.setStock(source.getStock());

        target.setType(resolveType(source.getType()));
        target.setKeywords(resolveKeywords(source.getKeywords()));
        return target;
    }

    private PublicationType resolveType(PublicationType type) {
        if (type == null) {
            return null;
        }
        if (type.getId() == null) {
            throw new ServiceException("Publication type id must be provided");
        }
        Long typeId = type.getId();
        LOG.debug("Resolving publication type {}", typeId);
        return publicationTypeRepository.findById(typeId)
            .orElseThrow(() -> new ResourceNotFoundException("Publication type not found: " + typeId));
    }

    private Set<Keyword> resolveKeywords(Set<Keyword> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            return new LinkedHashSet<>();
        }
        boolean missingId = keywords.stream().anyMatch(keyword -> keyword.getId() == null);
        if (missingId) {
            throw new ServiceException("Keyword id must be provided for all keywords");
        }
        Set<Long> keywordIds = keywords.stream()
            .map(Keyword::getId)
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(LinkedHashSet::new));
        LOG.debug("Resolving keywords {}", keywordIds);
        return keywordIds.stream()
            .map(keywordId -> keywordRepository.findById(keywordId)
                .orElseThrow(() -> new ResourceNotFoundException("Keyword not found: " + keywordId)))
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
