import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

import {environment} from "../environments/environment";
import {Links} from "./links";
import {Visit} from "./visit";

@Injectable({providedIn: 'root'})
export class LinksService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public setLink(links: Links): Observable<Links> {
    return this.http.post<Links>(`${this.apiServerUrl}/links`, links);
  }

  public getVisits(): Observable<Visit[]> {
    return this.http.get<Visit[]>(`${this.apiServerUrl}/visits`);
  }

}
