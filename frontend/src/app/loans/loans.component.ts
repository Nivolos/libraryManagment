import {Component, OnInit} from '@angular/core';
import {Borrower} from '../models/borrower';
import {Loan} from '../models/loan';
import {Publication} from '../models/publication';
import {MasterDataService} from '../services/master-data.service';
import {LoanService} from '../services/loan.service';
import {PublicationService} from '../services/publication.service';

@Component({
  selector: 'app-loans',
  templateUrl: './loans.component.html',
  styleUrls: ['./loans.component.css']
})
export class LoansComponent implements OnInit {

  openLoans: Loan[] = [];
  publications: Publication[] = [];
  borrowers: Borrower[] = [];
  borrowForm = {publicationId: undefined as number | undefined, borrowerId: undefined as number | undefined};
  errorMessage = '';

  constructor(private readonly loanService: LoanService,
              private readonly publicationService: PublicationService,
              private readonly masterDataService: MasterDataService) {
  }

  ngOnInit(): void {
    this.reloadLoans();
    this.loadMasterData();
  }

  reloadLoans(): void {
    this.loanService.listOpenLoans().subscribe({
      next: loans => this.openLoans = loans,
      error: error => this.errorMessage = error.error?.message ?? 'Ladevorgang fehlgeschlagen.'
    });
  }

  borrow(): void {
    if (!this.borrowForm.publicationId || !this.borrowForm.borrowerId) {
      return;
    }
    this.loanService.borrowPublication({
      publicationId: this.borrowForm.publicationId,
      borrowerId: this.borrowForm.borrowerId
    }).subscribe({
      next: () => {
        this.borrowForm = {publicationId: undefined, borrowerId: undefined};
        this.reloadLoans();
      },
      error: error => this.errorMessage = error.error?.message ?? 'Ausleihe fehlgeschlagen.'
    });
  }

  returnLoan(loan: Loan): void {
    if (!loan.id) {
      return;
    }
    this.loanService.returnLoan(loan.id).subscribe({
      next: () => this.reloadLoans(),
      error: error => this.errorMessage = error.error?.message ?? 'Rückgabe fehlgeschlagen.'
    });
  }

  private loadMasterData(): void {
    this.publicationService.listPublications().subscribe(publications => this.publications = publications);
    this.masterDataService.listBorrowers().subscribe(borrowers => this.borrowers = borrowers);
  }
}
