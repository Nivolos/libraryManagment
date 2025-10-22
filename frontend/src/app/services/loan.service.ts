import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Loan} from '../models/loan';

export interface BorrowRequest {
  publicationId: number;
  borrowerId: number;
}

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  private readonly resourceUrl = `${environment.apiBaseUrl}/loans`;

  constructor(private readonly http: HttpClient) {
  }

  listOpenLoans(): Observable<Loan[]> {
    return this.http.get<Loan[]>(`${this.resourceUrl}/open`);
  }

  borrowPublication(request: BorrowRequest): Observable<Loan> {
    return this.http.post<Loan>(`${this.resourceUrl}/borrow`, request);
  }

  returnLoan(loanId: number): Observable<Loan> {
    return this.http.post<Loan>(`${this.resourceUrl}/${loanId}/return`, {});
  }
}
