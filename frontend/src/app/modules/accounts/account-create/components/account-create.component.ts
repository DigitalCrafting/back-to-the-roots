import {Component, EventEmitter, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AccountsService} from "../../../../services/accounts.service";
import {AccountTypeEnum, CurrencyEnum} from "../../../../models/enums";
import {EregoldUserContext} from "../../../../context/eregold-user-context";

@Component({
    selector: 'account-create',
    templateUrl: './account-create.component.html',
    styleUrls: ['./account-create.component.scss']
})
export class AccountCreateComponent implements OnInit {
    static DEFAULT_DEBIT_NAME: string = "Debit account";
    static DEFAULT_SAVING_NAME: string = "Saving account";
    accountCreateEventEmitter: EventEmitter<AccountCreateAction> = new EventEmitter<AccountCreateAction>();

    formGroup = new FormGroup({
        accountName: new FormControl(AccountCreateComponent.DEFAULT_DEBIT_NAME, Validators.required),
        accountType: new FormControl(AccountTypeEnum.DEBIT, Validators.required),
        currency: new FormControl(CurrencyEnum.GLD, Validators.required)
    });

    currencyOptions = [
        {
            value: CurrencyEnum.GLD,
            display: CurrencyEnum.GLD
        }
    ];

    accountTypeOptions = [
        {
            value: AccountTypeEnum.DEBIT,
            display: 'Debit'
        },
        {
            value: AccountTypeEnum.SAVING,
            display: 'Saving'
        }
    ]

    constructor(
        private _eregoldUserContext: EregoldUserContext,
        private _accountsService: AccountsService) {
    }

    ngOnInit() {
        this.formGroup.get('accountType').valueChanges.subscribe((value) => {
            if (value === AccountTypeEnum.DEBIT && this.formGroup.get("accountName").value === AccountCreateComponent.DEFAULT_SAVING_NAME) {
                this.formGroup.get("accountName").setValue(AccountCreateComponent.DEFAULT_DEBIT_NAME);
            } else if (value === AccountTypeEnum.SAVING && this.formGroup.get("accountName").value === AccountCreateComponent.DEFAULT_DEBIT_NAME) {
                this.formGroup.get("accountName").setValue(AccountCreateComponent.DEFAULT_SAVING_NAME);
            }
        });
    }

    onCancelClicked() {
        this.accountCreateEventEmitter.emit(AccountCreateAction.SHOW_LIST);
    }

    onAddClicked() {
        if (this.formGroup.valid) {
            this._accountsService.createAccount(this.formGroup.value).subscribe(async response => {
                await this._eregoldUserContext.getAccounts(true);
                this.accountCreateEventEmitter.emit(AccountCreateAction.SHOW_LIST);
            });
        }
    }
}

export enum AccountCreateAction {
    SHOW_LIST = 'SHOW_LIST'
}