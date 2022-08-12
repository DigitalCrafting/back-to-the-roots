import {Component, EventEmitter, OnInit} from '@angular/core';
import {AccountModel} from "../../../../models/account.models";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CurrencyEnum} from "../../../../models/enums";
import {EregoldUserContext} from "../../../../context/eregold-user-context";
import {TransactionsService} from "../../../../services/transactions.service";

@Component({
    selector: 'own-deposit',
    templateUrl: './own-deposit.component.html',
    styleUrls: ['./own-deposit.component.scss']
})
export class OwnDepositComponent implements OnInit {
    backEventEmitter: EventEmitter<any> = new EventEmitter<any>();

    dstAccountList: Array<AccountModel>;

    formGroup: FormGroup = new FormGroup({
        dstAccount: new FormControl('', Validators.required),
        description: new FormControl('Deposit', Validators.required),
        amount: new FormControl(null, Validators.required), // TODO regex to match 2 decimal places
        currency: new FormControl({
            value: CurrencyEnum.GLD,
            disabled: true
        })
    });

    private destAccountNumber: string;
    private allAccountsList: Array<AccountModel>;

    constructor(private _userContext: EregoldUserContext,
                private _transactionsService: TransactionsService) {
    }

    setContext(accountNumber: string) {
        this.destAccountNumber = accountNumber;
    }

    async ngOnInit() {
        this.allAccountsList = await this._userContext.getAccounts();
        this.dstAccountList = this.allAccountsList;

        if (this.destAccountNumber) {
            this.formGroup.get('dstAccount').setValue(this.destAccountNumber);
            this.formGroup.get('dstAccount').disable();
        }
    }

    onCancelClicked() {
        this.backEventEmitter.emit();
    }

    onDepositClicked() {
        if (this.formGroup.valid) {
            this._transactionsService.deposit(this.formGroup.getRawValue()).subscribe(async () => {
                await this._userContext.getAccounts(true);
                this.backEventEmitter.emit();
            });
        }
    }
}
