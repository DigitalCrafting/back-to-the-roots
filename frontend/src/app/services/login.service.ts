import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class LoginService {
    // TODO user environment variable to distinguish different hosts
    private baseUrl = "http://localhost:8080/login";

    constructor(private _httpClient: HttpClient) {
    }

    public login(request: LoginRequest): Promise<LoginResponse> {
        let httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
            }),
            withCredentials: true
        };
        // @ts-ignore
        return this._httpClient.post(`${this.baseUrl}`, request, httpOptions).toPromise();
    }
}

export interface LoginRequest {
    userId: string;
    password: [];
}

export interface LoginResponse {
    token: string;
}
