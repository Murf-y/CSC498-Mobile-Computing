import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { GuessPage } from './guess.page';

const routes: Routes = [
  {
    path: '',
    component: GuessPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class GuessPageRoutingModule {}
