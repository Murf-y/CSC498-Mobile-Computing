import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Dog, DogResponse } from '../../models/dog';
import { DogService } from '../../services/dog.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  public loaded = false;
  public name = '';
  public dog : Dog = {
    image_url: '',
    breed: ''
  };

  constructor(private dogService : DogService, private router: Router) {}
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

  guess(){
    if(this.name.length > 0){
      const name = this.name.replace(/\s/g, '');
      this.router.navigate([`/guess/${name}`]);
    }
  }

}
