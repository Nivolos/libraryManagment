package de.nordakademie.iaa.library.service;

import de.nordakademie.iaa.library.dao.LoanRepository;
import de.nordakademie.iaa.library.model.Loan;
import de.nordakademie.iaa.library.model.Publication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    private static final int LOAN_PERIOD_DAYS = 14;

    private final LoanRepository loanRepository;
    private final PublicationService publicationService;
    private final MasterDataService masterDataService;

    public LoanServiceImpl(LoanRepository loanRepository,
                           PublicationService publicationService,
                           MasterDataService masterDataService) {
        this.loanRepository = loanRepository;
        this.publicationService = publicationService;
        this.masterDataService = masterDataService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> listOpenLoans() {
        return loanRepository.findByReturnedAtIsNullOrderByDueAtAsc();
    }

    @Override
    public Loan borrowPublication(long publicationId, long borrowerId) {
        Publication publication = publicationService.loadPublication(publicationId);
        long openLoansForPublication = loanRepository.countByPublicationIdAndReturnedAtIsNull(publicationId);
        if (openLoansForPublication >= publication.getStock()) {
            throw new ServiceException("No copies available for borrowing");
        }

        Loan loan = new Loan();
        loan.setPublication(publication);
        loan.setBorrower(masterDataService.loadBorrower(borrowerId));
        loan.setIssuedAt(LocalDate.now());
        loan.setDueAt(LocalDate.now().plusDays(LOAN_PERIOD_DAYS));
        return loanRepository.save(loan);
    }

    @Override
    public Loan returnLoan(long loanId) {
        Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        if (loan.isReturned()) {
            return loan;
        }
        loan.setReturnedAt(LocalDate.now());
        return loanRepository.save(loan);
    }
}
