package de.nordakademie.iaa.library.web;

import de.nordakademie.iaa.library.model.Loan;
import de.nordakademie.iaa.library.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/open")
    public List<Loan> listOpenLoans() {
        return loanService.listOpenLoans();
    }

    @PostMapping("/borrow")
    @ResponseStatus(HttpStatus.CREATED)
    public Loan borrow(@RequestBody BorrowRequest request) {
        return loanService.borrowPublication(request.publicationId(), request.borrowerId());
    }

    @PostMapping("/{loanId}/return")
    public Loan returnLoan(@PathVariable long loanId) {
        return loanService.returnLoan(loanId);
    }

    public record BorrowRequest(long publicationId, long borrowerId) {
    }
}
