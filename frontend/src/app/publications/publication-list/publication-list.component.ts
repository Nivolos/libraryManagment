import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Publication} from '../../models/publication';

@Component({
  selector: 'app-publication-list',
  templateUrl: './publication-list.component.html',
  styleUrls: ['./publication-list.component.css']
})
export class PublicationListComponent {

  @Input() publications: Publication[] = [];
  @Input() selectedPublicationId: number | null = null;
  @Output() selectPublication = new EventEmitter<Publication>();
  @Output() deletePublication = new EventEmitter<Publication>();

  trackById(_: number, publication: Publication): number | undefined {
    return publication.id;
  }

  onSelect(publication: Publication): void {
    this.selectPublication.emit(publication);
  }

  onDelete(publication: Publication, event: Event): void {
    event.stopPropagation();
    this.deletePublication.emit(publication);
  }
}
