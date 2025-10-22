import {Component, OnInit} from '@angular/core';
import {Borrower} from '../models/borrower';
import {Keyword} from '../models/keyword';
import {PublicationType} from '../models/publication-type';
import {MasterDataService} from '../services/master-data.service';

@Component({
  selector: 'app-masters',
  templateUrl: './masters.component.html',
  styleUrls: ['./masters.component.css']
})
export class MastersComponent implements OnInit {

  borrowers: Borrower[] = [];
  keywords: Keyword[] = [];
  types: PublicationType[] = [];

  borrowerDraft: Borrower = {matriculation: '', firstName: '', lastName: '', email: ''};
  keywordDraft: Keyword = {name: ''};
  typeDraft: PublicationType = {name: ''};

  errorMessage = '';

  constructor(private readonly masterDataService: MasterDataService) {
  }

  ngOnInit(): void {
    this.reload();
  }

  editBorrower(borrower: Borrower): void {
    this.borrowerDraft = {...borrower};
  }

  saveBorrower(): void {
    this.masterDataService.saveBorrower(this.borrowerDraft).subscribe({
      next: saved => {
        this.borrowerDraft = {matriculation: '', firstName: '', lastName: '', email: ''};
        this.replaceBorrower(saved);
      },
      error: error => this.errorMessage = error.error?.message ?? 'Speichern fehlgeschlagen.'
    });
  }

  deleteBorrower(borrower: Borrower): void {
    if (!borrower.id) {
      return;
    }
    this.masterDataService.deleteBorrower(borrower).subscribe({
      next: () => this.borrowers = this.borrowers.filter(item => item.id !== borrower.id),
      error: error => this.errorMessage = error.error?.message ?? 'Löschen fehlgeschlagen.'
    });
  }

  editKeyword(keyword: Keyword): void {
    this.keywordDraft = {...keyword};
  }

  saveKeyword(): void {
    this.masterDataService.saveKeyword(this.keywordDraft).subscribe({
      next: saved => {
        this.keywordDraft = {name: ''};
        this.replaceKeyword(saved);
      },
      error: error => this.errorMessage = error.error?.message ?? 'Speichern fehlgeschlagen.'
    });
  }

  deleteKeyword(keyword: Keyword): void {
    if (!keyword.id) {
      return;
    }
    this.masterDataService.deleteKeyword(keyword).subscribe({
      next: () => this.keywords = this.keywords.filter(item => item.id !== keyword.id),
      error: error => this.errorMessage = error.error?.message ?? 'Löschen fehlgeschlagen.'
    });
  }

  editType(type: PublicationType): void {
    this.typeDraft = {...type};
  }

  saveType(): void {
    this.masterDataService.savePublicationType(this.typeDraft).subscribe({
      next: saved => {
        this.typeDraft = {name: ''};
        this.replaceType(saved);
      },
      error: error => this.errorMessage = error.error?.message ?? 'Speichern fehlgeschlagen.'
    });
  }

  deleteType(type: PublicationType): void {
    if (!type.id) {
      return;
    }
    this.masterDataService.deletePublicationType(type).subscribe({
      next: () => this.types = this.types.filter(item => item.id !== type.id),
      error: error => this.errorMessage = error.error?.message ?? 'Löschen fehlgeschlagen.'
    });
  }

  private reload(): void {
    this.masterDataService.listBorrowers().subscribe(borrowers => this.borrowers = borrowers);
    this.masterDataService.listKeywords().subscribe(keywords => this.keywords = keywords);
    this.masterDataService.listPublicationTypes().subscribe(types => this.types = types);
  }

  private replaceBorrower(borrower: Borrower): void {
    const without = this.borrowers.filter(item => item.id !== borrower.id);
    this.borrowers = [...without, borrower]
      .sort((left, right) => left.lastName.localeCompare(right.lastName));
  }

  private replaceKeyword(keyword: Keyword): void {
    const without = this.keywords.filter(item => item.id !== keyword.id);
    this.keywords = [...without, keyword]
      .sort((left, right) => left.name.localeCompare(right.name));
  }

  private replaceType(type: PublicationType): void {
    const without = this.types.filter(item => item.id !== type.id);
    this.types = [...without, type]
      .sort((left, right) => left.name.localeCompare(right.name));
  }
}
