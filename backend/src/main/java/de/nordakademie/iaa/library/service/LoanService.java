package de.nordakademie.iaa.library.service;

import de.nordakademie.iaa.library.model.Loan;

import java.util.List;

public interface LoanService {

    List<Loan> listOpenLoans();

    Loan borrowPublication(long publicationId, long borrowerId);

    Loan returnLoan(long loanId);
}
