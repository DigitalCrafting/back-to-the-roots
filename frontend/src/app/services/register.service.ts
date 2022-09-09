import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class RegisterService {
    // TODO user environment variable to distinguish different hosts
    private baseUrl = "http://localhost:8080/registration";
    private httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        }),
        withCredentials: true
    };

    constructor(private _httpClient: HttpClient) {
    }

    public register(request: RegisterRequest): Promise<any> {
        return this._httpClient.post(this.baseUrl, request, this.httpOptions).toPromise();
    }
}

export interface RegisterRequest {
    email: string;
    password: [];
    firstName: string;
    lastName: string;
}

export enum RegisterStatusEnum {
    CREATED = 'CREATED',
    ALREADY_EXISTS = 'ALREADY_EXISTS',
    CREATION_FAILED = 'CREATION_FAILED'
}

export interface RegisterResponse {
    customerId: string;
    status: RegisterStatusEnum;
}
