import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {LoansComponent} from './loans/loans.component';
import {MastersComponent} from './masters/masters.component';
import {PublicationFormComponent} from './publications/publication-form/publication-form.component';
import {PublicationListComponent} from './publications/publication-list/publication-list.component';
import {PublicationsComponent} from './publications/publications.component';

@NgModule({
  declarations: [
    AppComponent,
    PublicationsComponent,
    PublicationListComponent,
    PublicationFormComponent,
    LoansComponent,
    MastersComponent
  ],
  imports: [BrowserModule, FormsModule, HttpClientModule, AppRoutingModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
