import {Borrower} from './borrower';
import {Publication} from './publication';

export interface Loan {
  id?: number;
  publication: Publication;
  borrower: Borrower;
  issuedAt: string;
  dueAt: string;
  returnedAt?: string | null;
}
