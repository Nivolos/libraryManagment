package de.nordakademie.iaa.library.web.dto;

import de.nordakademie.iaa.library.model.Keyword;
import de.nordakademie.iaa.library.model.Publication;
import de.nordakademie.iaa.library.model.PublicationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Objects;
import java.util.stream.Collectors;

public class PublicationRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Authors are required")
    private String authors;

    private LocalDate publishedAt;

    private String publisher;

    private String isbn;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be zero or positive")
    private Integer stock;

    private Long typeId;

    private Set<Long> keywordIds = new LinkedHashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public LocalDate getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDate publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Set<Long> getKeywordIds() {
        return new LinkedHashSet<>(keywordIds);
    }

    public void setKeywordIds(Set<Long> keywordIds) {
        this.keywordIds = keywordIds == null ? new LinkedHashSet<>() : new LinkedHashSet<>(keywordIds);
    }

    public void setType(PublicationType type) {
        this.typeId = type == null ? null : type.getId();
    }

    public void setKeywords(Set<Keyword> keywords) {
        if (keywords == null) {
            this.keywordIds = new LinkedHashSet<>();
            return;
        }
        this.keywordIds = keywords.stream()
            .map(Keyword::getId)
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Publication toPublication() {
        Publication publication = new Publication();
        publication.setTitle(title);
        publication.setAuthors(authors);
        publication.setPublishedAt(publishedAt);
        publication.setPublisher(publisher);
        publication.setIsbn(isbn);
        publication.setStock(stock == null ? 0 : stock);
        publication.setType(buildType());
        publication.setKeywords(buildKeywords());
        return publication;
    }

    public int keywordCount() {
        return keywordIds == null ? 0 : keywordIds.size();
    }

    private PublicationType buildType() {
        if (typeId == null) {
            return null;
        }
        PublicationType type = new PublicationType();
        type.setId(typeId);
        return type;
    }

    private Set<Keyword> buildKeywords() {
        if (keywordIds == null || keywordIds.isEmpty()) {
            return new LinkedHashSet<>();
        }
        Set<Keyword> keywords = new LinkedHashSet<>();
        for (Long keywordId : keywordIds) {
            if (keywordId == null) {
                continue;
            }
            Keyword keyword = new Keyword();
            keyword.setId(keywordId);
            keywords.add(keyword);
        }
        return keywords;
    }
}
