import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CountryResponse } from '../models/country';

@Injectable({
  providedIn: 'root'
})
export class CountryService {
  public getCountry(name: string): Observable<CountryResponse> {
    return this.httpClient.get<CountryResponse>('https://api.nationalize.io/?name='+ name);
  }
  constructor(private httpClient: HttpClient) { }
}
