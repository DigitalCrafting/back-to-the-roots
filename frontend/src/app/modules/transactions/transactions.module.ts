import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TransactionsHistoryComponent} from './components/transactions-history.component';


@NgModule({
    declarations: [
        TransactionsHistoryComponent
    ],
    exports: [
        TransactionsHistoryComponent
    ],
    imports: [
        CommonModule
    ]
})
export class TransactionsModule {
}
