import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoansComponent} from './loans/loans.component';
import {MastersComponent} from './masters/masters.component';
import {PublicationsComponent} from './publications/publications.component';

const routes: Routes = [
  {path: '', redirectTo: 'publications', pathMatch: 'full'},
  {path: 'publications', component: PublicationsComponent},
  {path: 'loans', component: LoansComponent},
  {path: 'masters', component: MastersComponent},
  {path: '**', redirectTo: 'publications'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
