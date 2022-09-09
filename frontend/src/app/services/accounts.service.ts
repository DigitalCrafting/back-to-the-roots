import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CreateAccountRequest} from "../models/account.models";

@Injectable({
    providedIn: 'root'
})
export class AccountsService {
    private baseUrl = "http://localhost:8080/v1/accounts";
    private httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        }),
        withCredentials: true
    };

    constructor(private _httpClient: HttpClient) {
    }

    // TODO change observable to Promise OR introduce dispatcher service that handles errors
    public getAccounts(): Promise<any> {
        return this._httpClient.get(this.baseUrl, this.httpOptions).toPromise();
    }

    public getAccountDetails(accountNumber: string): Promise<any> {
        return this._httpClient.get(`${this.baseUrl}/${accountNumber}`, this.httpOptions).toPromise();
    }

    public createAccount(request: CreateAccountRequest) {
        return this._httpClient.post(this.baseUrl, request, this.httpOptions).toPromise();
    }

}
