import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Borrower} from '../models/borrower';
import {Keyword} from '../models/keyword';
import {PublicationType} from '../models/publication-type';

@Injectable({
  providedIn: 'root'
})
export class MasterDataService {

  private readonly resourceUrl = `${environment.apiBaseUrl}/masters`;

  constructor(private readonly http: HttpClient) {
  }

  listBorrowers(): Observable<Borrower[]> {
    return this.http.get<Borrower[]>(`${this.resourceUrl}/borrowers`);
  }

  saveBorrower(borrower: Borrower): Observable<Borrower> {
    if (borrower.id) {
      return this.http.put<Borrower>(`${this.resourceUrl}/borrowers/${borrower.id}`, borrower);
    }
    return this.http.post<Borrower>(`${this.resourceUrl}/borrowers`, borrower);
  }

  deleteBorrower(borrower: Borrower): Observable<void> {
    return this.http.delete<void>(`${this.resourceUrl}/borrowers/${borrower.id}`);
  }

  listKeywords(): Observable<Keyword[]> {
    return this.http.get<Keyword[]>(`${this.resourceUrl}/keywords`);
  }

  saveKeyword(keyword: Keyword): Observable<Keyword> {
    if (keyword.id) {
      return this.http.put<Keyword>(`${this.resourceUrl}/keywords/${keyword.id}`, keyword);
    }
    return this.http.post<Keyword>(`${this.resourceUrl}/keywords`, keyword);
  }

  deleteKeyword(keyword: Keyword): Observable<void> {
    return this.http.delete<void>(`${this.resourceUrl}/keywords/${keyword.id}`);
  }

  listPublicationTypes(): Observable<PublicationType[]> {
    return this.http.get<PublicationType[]>(`${this.resourceUrl}/publication-types`);
  }

  savePublicationType(type: PublicationType): Observable<PublicationType> {
    if (type.id) {
      return this.http.put<PublicationType>(`${this.resourceUrl}/publication-types/${type.id}`, type);
    }
    return this.http.post<PublicationType>(`${this.resourceUrl}/publication-types`, type);
  }

  deletePublicationType(type: PublicationType): Observable<void> {
    return this.http.delete<void>(`${this.resourceUrl}/publication-types/${type.id}`);
  }
}
