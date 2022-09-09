import {Component, EventEmitter, OnInit} from '@angular/core';
import {EregoldUserContext} from "../../../../context/eregold-user-context";
import {AccountModel} from "../../../../models/account.models";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {TransactionModel, TransactionsService} from "../../../../services/transactions.service";
import {CurrencyEnum} from "../../../../models/enums";
import {OwnTransferValidators} from "../validators/own-transfer.validators";
import {OwnTransferControlNames} from "../utils/own-transfer.utils";
import {ServiceDispatcher} from "../../../../core/service-dispatcher/service-dispatcher";

@Component({
    selector: 'own-transfer',
    templateUrl: './own-transfer.component.html',
    styleUrls: ['./own-transfer.component.scss']
})
export class OwnTransferComponent implements OnInit {

    backEventEmitter: EventEmitter<any> = new EventEmitter<any>();

    srcAccountList: Array<AccountModel>;
    dstAccountList: Array<AccountModel>;
    controlNames = OwnTransferControlNames;

    formGroup: FormGroup = new FormGroup({
        [OwnTransferControlNames.SRC_ACCOUNT]: new FormControl('', Validators.required),
        [OwnTransferControlNames.DST_ACCOUNT]: new FormControl('', Validators.required),
        [OwnTransferControlNames.DESCRIPTION]: new FormControl('', Validators.required),
        [OwnTransferControlNames.AMOUNT]: new FormControl(null, Validators.required), // TODO regex to match 2 decimal places
        [OwnTransferControlNames.CURRENCY]: new FormControl({
            value: CurrencyEnum.GLD,
            disabled: true
        })
    }, OwnTransferValidators.amountValidator);

    private initialAccountNumber: string;
    private allAccountsList: Array<AccountModel>;

    constructor(private _userContext: EregoldUserContext,
                private _transactionsService: TransactionsService) {
    }

    setContext(accountNumber: string) {
        this.initialAccountNumber = accountNumber;
    }

    async ngOnInit() {
        this.allAccountsList = await this._userContext.getAccounts();
        this.srcAccountList = [...this.allAccountsList];
        this.dstAccountList = [...this.allAccountsList];

        this.initFormGroupHandlers();
        if (this.initialAccountNumber) {
            let srcAccount = this.srcAccountList.find(a => a.accountNumber === this.initialAccountNumber);
            this.formGroup.get(OwnTransferControlNames.SRC_ACCOUNT).setValue(srcAccount);
            this.formGroup.get(OwnTransferControlNames.SRC_ACCOUNT).disable();
        }
    }

    onCancelClicked() {
        this.backEventEmitter.emit();
    }

    onTransferClicked() {
        if (this.formGroup.valid) {
            ServiceDispatcher.dispatch(this._transactionsService.transfer(this.createTransferRequest())).then(async () => {
                await this._userContext.getAccounts(true);
                this.backEventEmitter.emit();
            });
        }
    }

    private initFormGroupHandlers() {
        this.formGroup.valueChanges.subscribe(val => console.log(val))
        this.formGroup.get(OwnTransferControlNames.SRC_ACCOUNT).valueChanges.subscribe(chosenAcc => {
            console.log(chosenAcc)
            this.dstAccountList = this.allAccountsList.filter(account => account.accountNumber !== chosenAcc.accountNumber)
            if (this.dstAccountList.length > 0) {
                this.formGroup.get(OwnTransferControlNames.DST_ACCOUNT).setValue(this.dstAccountList[0]);
            } else {
                this.formGroup.get(OwnTransferControlNames.DST_ACCOUNT).reset();
            }
        });
    }

    private createTransferRequest() {
        const rawValue = this.formGroup.getRawValue();
        return {
            srcAccount: rawValue.srcAccount.accountNumber,
            dstAccount: rawValue.dstAccount.accountNumber,
            description: rawValue.description,
            amount: rawValue.amount,
            currency: rawValue.currency
        } as TransactionModel;
    }
}
