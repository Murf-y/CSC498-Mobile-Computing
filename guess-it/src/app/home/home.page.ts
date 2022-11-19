import { Component } from '@angular/core';
import { Dog, DogResponse } from '../models/dog';
import { DogService } from '../services/dog.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  public loaded = false;
  public dog : Dog = {
    image_url: '',
    breed: ''
  };

  constructor(private dogService : DogService) {}
  ionViewDidEnter() {
    this.getDog();
  }

  getDog(){
    this.loaded = false;
    this.dogService.getDog().subscribe((response: DogResponse) => {
      const breed = response.message.split('/')[4];
      this.dog = {
        image_url: response.message,
        breed
      };
      this.loaded = true;
    });
  }

}
