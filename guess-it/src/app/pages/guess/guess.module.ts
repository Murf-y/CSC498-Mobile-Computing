import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { GuessPageRoutingModule } from './guess-routing.module';

import { GuessPage } from './guess.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    GuessPageRoutingModule
  ],
  declarations: [GuessPage]
})
export class GuessPageModule {}
