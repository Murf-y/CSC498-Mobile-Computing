
export interface Country {
  country_id: string,
  probability: number,
}

export interface CountryResponse {
  country: Country[],
  name: string,
}
