import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {Keyword} from '../../models/keyword';
import {Publication} from '../../models/publication';
import {PublicationType} from '../../models/publication-type';

@Component({
  selector: 'app-publication-form',
  templateUrl: './publication-form.component.html',
  styleUrls: ['./publication-form.component.css']
})
export class PublicationFormComponent implements OnChanges {

  @Input() publication!: Publication;
  @Input() types: PublicationType[] = [];
  @Input() keywords: Keyword[] = [];
  @Output() save = new EventEmitter<Publication>();
  @Output() cancel = new EventEmitter<void>();

  selectedKeywordIds: number[] = [];
  selectedTypeId?: number;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['publication'] && this.publication) {
      this.selectedKeywordIds = (this.publication.keywords ?? [])
        .map(keyword => keyword.id!)
        .filter(id => id !== undefined);
      this.selectedTypeId = this.publication.type?.id ?? undefined;
    }
  }

  onSubmit(): void {
    const keywordMap = new Map(this.keywords.map(keyword => [keyword.id, keyword] as const));
    const normalizedKeywords = this.selectedKeywordIds
      .map(id => keywordMap.get(id))
      .filter((keyword): keyword is Keyword => !!keyword);

    const normalized: Publication = {
      ...this.publication,
      keywords: normalizedKeywords,
      type: this.types.find(type => type.id === this.selectedTypeId) ?? null
    };
    this.save.emit(normalized);
  }

  onCancel(): void {
    this.cancel.emit();
  }
}
