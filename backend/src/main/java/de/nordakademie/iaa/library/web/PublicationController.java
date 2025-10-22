package de.nordakademie.iaa.library.web;

import de.nordakademie.iaa.library.model.Publication;
import de.nordakademie.iaa.library.service.PublicationService;
import de.nordakademie.iaa.library.web.dto.PublicationRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    private static final Logger LOG = LoggerFactory.getLogger(PublicationController.class);

    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping
    public List<Publication> listPublications(@RequestParam(required = false) String query,
                                              @RequestParam(required = false) Long typeId,
                                              @RequestParam(required = false, name = "keywordId") List<Long> keywordIds) {
        Set<Long> keywords = keywordIds == null ? new HashSet<>() : new HashSet<>(keywordIds);
        return publicationService.listPublications(query, typeId, keywords);
    }

    @GetMapping("/{publicationId}")
    public Publication loadPublication(@PathVariable long publicationId) {
        return publicationService.loadPublication(publicationId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Publication createPublication(@Valid @RequestBody PublicationRequest request) {
        LOG.info("Creating publication title='{}', typeId={}, keywords={}",
            request.getTitle(), request.getTypeId(), request.keywordCount());
        Publication publication = request.toPublication();
        return publicationService.savePublication(publication);
    }

    @PutMapping("/{publicationId}")
    public Publication updatePublication(@PathVariable long publicationId,
                                         @Valid @RequestBody PublicationRequest request) {
        LOG.info("Updating publication id={} title='{}'", publicationId, request.getTitle());
        Publication publication = request.toPublication();
        publication.setId(publicationId);
        return publicationService.updatePublication(publicationId, publication);
    }

    @DeleteMapping("/{publicationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublication(@PathVariable long publicationId) {
        publicationService.deletePublication(publicationId);
    }
}
