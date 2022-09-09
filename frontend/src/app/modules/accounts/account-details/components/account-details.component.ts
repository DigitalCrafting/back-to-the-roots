import {Component, EventEmitter, OnInit} from '@angular/core';
import {AccountsService} from "../../../../services/accounts.service";
import {AccountDetailsModel} from "../../../../models/account.models";
import {ServiceDispatcher} from "../../../../core/service-dispatcher/service-dispatcher";

@Component({
    selector: 'account-details',
    templateUrl: './account-details.component.html',
    styleUrls: ['./account-details.component.scss']
})
export class AccountDetailsComponent implements OnInit {

    backToDetailsEventEmitter: EventEmitter<any> = new EventEmitter<any>();
    makeOwnTransferEventEmitter: EventEmitter<any> = new EventEmitter<any>();
    makeOwnDepositEventEmitter: EventEmitter<any> = new EventEmitter<any>();

    accountDetails: AccountDetailsModel;

    private accountNumber: string;

    constructor(private _accountsService: AccountsService) {
    }

    setContext(accountNumber: string) {
        this.accountNumber = accountNumber;
    }

    async ngOnInit() {
        this.accountDetails = await ServiceDispatcher.dispatch(this._accountsService.getAccountDetails(this.accountNumber));
    }

    onGoBackClicked() {
        this.backToDetailsEventEmitter.emit();
    }

    onMakeOwnTransferClicked() {
        this.makeOwnTransferEventEmitter.emit();
    }

    onMakeOwnDepositClicked() {
        this.makeOwnDepositEventEmitter.emit();
    }
}
