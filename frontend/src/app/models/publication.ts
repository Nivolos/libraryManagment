import {Keyword} from './keyword';
import {PublicationType} from './publication-type';

export interface Publication {
  id?: number;
  title: string;
  authors: string;
  publishedAt?: string;
  publisher?: string;
  isbn?: string;
  stock: number;
  type?: PublicationType | null;
  keywords: Keyword[];
}
