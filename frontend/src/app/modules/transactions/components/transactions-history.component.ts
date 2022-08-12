import {Component, Input} from '@angular/core';
import {TransactionModel} from "../../../models/transaction.models";

@Component({
    selector: 'transactions-history',
    templateUrl: './transactions-history.component.html',
    styleUrls: ['./transactions-history.component.scss']
})
export class TransactionsHistoryComponent {

    @Input()
    transactionsHistory: Array<TransactionModel>
}
