import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Age } from 'src/app/models/age';
import { Country } from 'src/app/models/country';
import { Gender } from 'src/app/models/gender';
import { AgeService } from 'src/app/services/age.service';
import { CountryService } from 'src/app/services/country.service';
import { GenderService } from 'src/app/services/gender.service';

@Component({
  selector: 'app-guess',
  templateUrl: './guess.page.html',
  styleUrls: ['./guess.page.scss'],
})
export class GuessPage implements OnInit {

  public name: string = '';

  public loadedGender = false;
  public gender: Gender | undefined;

  public loadedAge = false;
  public age: Age | undefined;

  public loadedNationality = false;
  public country: Country | undefined;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private genderService: GenderService,
    private ageService: AgeService, private countryService: CountryService) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(paramMap => {
      if (!paramMap.has('name')) {
        this.router.navigate(['/']);
      }
      this.name = paramMap.get('name') ?? '';

      if(this.name.length === 0){
        this.router.navigate(['/']);
      }
    });
  }

  ionViewWillEnter() {
    this.genderService.getGender(this.name).subscribe((response) => {
      this.gender = {
        probability: Math.round(response.probability * 100),
        gender: response.gender,
      };
      this.loadedGender = true;
    });

    this.ageService.getAge(this.name).subscribe((response) => {
      this.age = {
        age: response.age,
      }
      this.loadedAge = true;
    });

    this.countryService.getCountry(this.name).subscribe((response) => {
      this.country = {
        country_id: response.country[0].country_id,
        probability: Math.round(response.country[0].probability * 100),
      }

      this.loadedNationality = true;
    });

  }

  goBack(){
    this.router.navigate(['/']);
  }

}
