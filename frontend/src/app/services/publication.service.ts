import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Publication} from '../models/publication';

export interface PublicationFilter {
  query?: string;
  typeId?: number;
  keywordIds?: number[];
}

@Injectable({
  providedIn: 'root'
})
export class PublicationService {

  private readonly resourceUrl = `${environment.apiBaseUrl}/publications`;

  constructor(private readonly http: HttpClient) {
  }

  listPublications(filter?: PublicationFilter): Observable<Publication[]> {
    let params = new HttpParams();
    if (filter?.query) {
      params = params.set('query', filter.query);
    }
    if (filter?.typeId) {
      params = params.set('typeId', filter.typeId);
    }
    if (filter?.keywordIds?.length) {
      filter.keywordIds.forEach(keywordId => {
        params = params.append('keywordId', keywordId);
      });
    }
    return this.http.get<Publication[]>(this.resourceUrl, {params});
  }

  loadPublication(publicationId: number): Observable<Publication> {
    return this.http.get<Publication>(`${this.resourceUrl}/${publicationId}`);
  }

  savePublication(publication: Publication): Observable<Publication> {
    if (publication.id) {
      return this.http.put<Publication>(`${this.resourceUrl}/${publication.id}`, publication);
    }
    return this.http.post<Publication>(this.resourceUrl, publication);
  }

  deletePublication(publication: Publication): Observable<void> {
    return this.http.delete<void>(`${this.resourceUrl}/${publication.id}`);
  }
}
