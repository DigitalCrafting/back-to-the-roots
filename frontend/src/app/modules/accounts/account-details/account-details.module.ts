import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AccountDetailsComponent} from './components/account-details.component';
import {TransactionsModule} from "../../transactions/transactions.module";
import {HttpClientModule} from "@angular/common/http";


@NgModule({
    declarations: [
        AccountDetailsComponent
    ],
    imports: [
        CommonModule,
        HttpClientModule,
        TransactionsModule
    ]
})
export class AccountDetailsModule {
}
