package de.nordakademie.iaa.library.service;

import de.nordakademie.iaa.library.model.Borrower;
import de.nordakademie.iaa.library.model.Keyword;
import de.nordakademie.iaa.library.model.PublicationType;

import java.util.List;

public interface MasterDataService {

    List<Borrower> listBorrowers();

    Borrower saveBorrower(Borrower borrower);

    void deleteBorrower(long borrowerId);

    Borrower loadBorrower(long borrowerId);

    List<Keyword> listKeywords();

    Keyword saveKeyword(Keyword keyword);

    void deleteKeyword(long keywordId);

    List<PublicationType> listPublicationTypes();

    PublicationType savePublicationType(PublicationType type);

    void deletePublicationType(long typeId);
}
