import { Injectable } from '@angular/core';
import { Dog, DogResponse } from '../models/dog';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DogService {
  public getDog(): Observable<DogResponse> {
    return this.httpClient.get<DogResponse>('https://dog.ceo/api/breeds/image/random');
  }
  constructor(private httpClient: HttpClient) { }

}
