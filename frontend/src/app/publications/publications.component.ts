import {Component, OnInit} from '@angular/core';
import {Borrower} from '../models/borrower';
import {Keyword} from '../models/keyword';
import {Publication} from '../models/publication';
import {PublicationType} from '../models/publication-type';
import {MasterDataService} from '../services/master-data.service';
import {PublicationFilter, PublicationService} from '../services/publication.service';

@Component({
  selector: 'app-publications',
  templateUrl: './publications.component.html',
  styleUrls: ['./publications.component.css']
})
export class PublicationsComponent implements OnInit {

  publications: Publication[] = [];
  selectedPublication: Publication | null = null;
  filter: PublicationFilter = {keywordIds: []};
  keywordOptions: Keyword[] = [];
  typeOptions: PublicationType[] = [];
  borrowerOptions: Borrower[] = [];
  loading = false;
  errorMessage = '';

  constructor(private readonly publicationService: PublicationService,
              private readonly masterDataService: MasterDataService) {
  }

  ngOnInit(): void {
    this.loadMasterData();
    this.reloadPublications();
  }

  reloadPublications(): void {
    this.loading = true;
    this.publicationService.listPublications(this.filter).subscribe({
      next: publications => {
        this.publications = publications;
        this.loading = false;
      },
      error: error => {
        this.errorMessage = error.error?.message ?? 'Loading publications failed.';
        this.loading = false;
      }
    });
  }

  editPublication(publication: Publication): void {
    this.selectedPublication = this.clonePublication(publication);
  }

  createPublication(): void {
    this.selectedPublication = this.clonePublication({
      title: '',
      authors: '',
      stock: 1,
      keywords: [],
      type: null
    });
  }

  cancelEditing(): void {
    this.selectedPublication = null;
  }

  savePublication(publication: Publication): void {
    this.publicationService.savePublication(publication).subscribe({
      next: saved => {
        this.selectedPublication = null;
        this.replacePublication(saved);
      },
      error: error => {
        this.errorMessage = error.error?.message ?? 'Saving publication failed.';
      }
    });
  }

  deletePublication(publication: Publication): void {
    if (!publication.id) {
      return;
    }
    this.publicationService.deletePublication(publication).subscribe({
      next: () => {
        this.publications = this.publications.filter(item => item.id !== publication.id);
        if (this.selectedPublication?.id === publication.id) {
          this.selectedPublication = null;
        }
      },
      error: error => {
        this.errorMessage = error.error?.message ?? 'Deleting publication failed.';
      }
    });
  }

  updateFilterKeywords(keywordId: number, checked: boolean): void {
    const keywords = new Set(this.filter.keywordIds ?? []);
    if (checked) {
      keywords.add(keywordId);
    } else {
      keywords.delete(keywordId);
    }
    this.filter.keywordIds = Array.from(keywords.values());
  }

  private loadMasterData(): void {
    this.masterDataService.listKeywords().subscribe(keywords => this.keywordOptions = keywords);
    this.masterDataService.listPublicationTypes().subscribe(types => this.typeOptions = types);
    this.masterDataService.listBorrowers().subscribe(borrowers => this.borrowerOptions = borrowers);
  }

  private replacePublication(publication: Publication): void {
    const withoutPublication = this.publications.filter(item => item.id !== publication.id);
    this.publications = [...withoutPublication, publication]
      .sort((left, right) => left.title.localeCompare(right.title));
  }

  private clonePublication(publication: Publication): Publication {
    return {
      ...publication,
      keywords: publication.keywords ? publication.keywords.map(keyword => ({...keyword})) : [],
      type: publication.type ? {...publication.type} : null
    };
  }
}
