import {FormControl, FormGroup} from "@angular/forms";
import {OwnTransferControlNames} from "../utils/own-transfer.utils";
import {AccountTypeEnum, CurrencyEnum} from "../../../../models/enums";
import {AccountModel} from "../../../../models/account.models";
import {OwnTransferValidators} from "./own-transfer.validators";

describe('Own transfer validators', () => {

    describe('amountValidator', () => {
        const mockAccount = {
            accountNumber: '12ERGD12345',
            accountName: 'Test name',
            currentBalance: 12.0,
            currency: CurrencyEnum.GLD,
            type: AccountTypeEnum.DEBIT
        } as AccountModel;
        let srcAccountFormControl = new FormControl(mockAccount);
        let amountFormControl = new FormControl();

        let formGroup = new FormGroup({
            [OwnTransferControlNames.SRC_ACCOUNT]: srcAccountFormControl,
            [OwnTransferControlNames.AMOUNT]: amountFormControl,
        }, OwnTransferValidators.amountValidator)

        it('Should set formGroup to invalid when amount exceeds balance', () => {
            // when
            amountFormControl.setValue(100);

            // then
            expect(formGroup.invalid).toBeTruthy();
            expect(amountFormControl.invalid).toBeTruthy();
            expect(amountFormControl.getError('amountExceedsBalance')).toBeTruthy();
        });

        it('Should set formGroup to valid when amount does not exceed balance', () => {
            // when
            amountFormControl.setValue(10);

            // then
            expect(formGroup.valid).toBeTruthy();
            expect(amountFormControl.valid).toBeTruthy();
            expect(amountFormControl.getError('amountExceedsBalance')).toBeFalsy();
        });
    });
});