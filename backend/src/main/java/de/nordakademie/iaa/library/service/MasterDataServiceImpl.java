package de.nordakademie.iaa.library.service;

import de.nordakademie.iaa.library.dao.BorrowerRepository;
import de.nordakademie.iaa.library.dao.KeywordRepository;
import de.nordakademie.iaa.library.dao.PublicationTypeRepository;
import de.nordakademie.iaa.library.model.Borrower;
import de.nordakademie.iaa.library.model.Keyword;
import de.nordakademie.iaa.library.model.PublicationType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class MasterDataServiceImpl implements MasterDataService {

    private final BorrowerRepository borrowerRepository;
    private final KeywordRepository keywordRepository;
    private final PublicationTypeRepository publicationTypeRepository;

    public MasterDataServiceImpl(BorrowerRepository borrowerRepository,
                                 KeywordRepository keywordRepository,
                                 PublicationTypeRepository publicationTypeRepository) {
        this.borrowerRepository = borrowerRepository;
        this.keywordRepository = keywordRepository;
        this.publicationTypeRepository = publicationTypeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Borrower> listBorrowers() {
        return borrowerRepository.findAll().stream()
            .sorted(Comparator.comparing(Borrower::getLastName)
                .thenComparing(Borrower::getFirstName))
            .toList();
    }

    @Override
    public Borrower saveBorrower(Borrower borrower) {
        borrowerRepository.findByMatriculation(borrower.getMatriculation())
            .filter(existing -> borrower.getId() == null || !existing.getId().equals(borrower.getId()))
            .ifPresent(existing -> {
                throw new ServiceException("Borrower with matriculation already exists");
            });
        return borrowerRepository.save(borrower);
    }

    @Override
    public void deleteBorrower(long borrowerId) {
        if (!borrowerRepository.existsById(borrowerId)) {
            throw new ResourceNotFoundException("Borrower not found");
        }
        borrowerRepository.deleteById(borrowerId);
    }

    @Override
    @Transactional(readOnly = true)
    public Borrower loadBorrower(long borrowerId) {
        return borrowerRepository.findById(borrowerId)
            .orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Keyword> listKeywords() {
        return keywordRepository.findAll().stream()
            .sorted(Comparator.comparing(Keyword::getName, String.CASE_INSENSITIVE_ORDER))
            .toList();
    }

    @Override
    public Keyword saveKeyword(Keyword keyword) {
        keywordRepository.findByNameIgnoreCase(keyword.getName())
            .filter(existing -> keyword.getId() == null || !existing.getId().equals(keyword.getId()))
            .ifPresent(existing -> {
                throw new ServiceException("Keyword already exists");
            });
        return keywordRepository.save(keyword);
    }

    @Override
    public void deleteKeyword(long keywordId) {
        if (!keywordRepository.existsById(keywordId)) {
            throw new ResourceNotFoundException("Keyword not found");
        }
        keywordRepository.deleteById(keywordId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicationType> listPublicationTypes() {
        return publicationTypeRepository.findAll().stream()
            .sorted(Comparator.comparing(PublicationType::getName, String.CASE_INSENSITIVE_ORDER))
            .toList();
    }

    @Override
    public PublicationType savePublicationType(PublicationType type) {
        publicationTypeRepository.findByNameIgnoreCase(type.getName())
            .filter(existing -> type.getId() == null || !existing.getId().equals(type.getId()))
            .ifPresent(existing -> {
                throw new ServiceException("Publication type already exists");
            });
        return publicationTypeRepository.save(type);
    }

    @Override
    public void deletePublicationType(long typeId) {
        if (!publicationTypeRepository.existsById(typeId)) {
            throw new ResourceNotFoundException("Publication type not found");
        }
        publicationTypeRepository.deleteById(typeId);
    }
}
