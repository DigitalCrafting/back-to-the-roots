import {Component, EventEmitter, OnInit} from '@angular/core';
import {AccountModel} from "../../../../models/account.models";
import {EregoldUserContext} from "../../../../context/eregold-user-context";

@Component({
    selector: 'accounts-list',
    templateUrl: './accounts-list.component.html',
    styleUrls: ['./accounts-list.component.scss']
})
export class AccountsListComponent implements OnInit {

    createAccountEventEmitter: EventEmitter<any> = new EventEmitter();
    makeOwnTransferEventEmitter: EventEmitter<any> = new EventEmitter();
    makeOwnDepositEventEmitter: EventEmitter<any> = new EventEmitter();
    showDetailsEventEmitter: EventEmitter<string> = new EventEmitter<string>();
    accountsList: Array<AccountModel>;

    constructor(private _userContext: EregoldUserContext) {
    }

    async ngOnInit() {
        this.accountsList = await this._userContext.getAccounts();
    }

    onDetailsClicked(accountNumber: string) {
        this.showDetailsEventEmitter.emit(accountNumber);
    }

    onAddAccountClicked() {
        this.createAccountEventEmitter.emit();
    }

    onMakeOwnTransferClicked() {
        this.makeOwnTransferEventEmitter.emit();
    }

    onMakeOwnDepositClicked() {
        this.makeOwnDepositEventEmitter.emit();
    }
}
