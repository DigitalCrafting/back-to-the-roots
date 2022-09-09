import {Injectable} from '@angular/core';
import {CurrencyEnum} from "../models/enums";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class TransactionsService {
    private baseUrl = "http://localhost:8080/v1/transactions";
    private httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        }),
        withCredentials: true
    };

    constructor(private _httpClient: HttpClient) {
    }

    public transfer(request: TransactionModel) {
        return this._httpClient.post(this.baseUrl + '/transfer', request, this.httpOptions).toPromise();
    }

    public deposit(request: TransactionModel) {
        return this._httpClient.post(this.baseUrl + '/deposit', request, this.httpOptions).toPromise();
    }
}

export interface TransactionModel {
    srcAccount?: string;
    dstAccount?: string;
    description?: string;
    amount?: number;
    currency?: CurrencyEnum;
}