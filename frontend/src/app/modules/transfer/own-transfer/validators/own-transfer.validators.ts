import {AbstractControl} from "@angular/forms";
import {OwnTransferControlNames} from "../utils/own-transfer.utils";
import {AccountModel} from "../../../../models/account.models";

export namespace OwnTransferValidators {
    export function amountValidator(c: AbstractControl): any {
        const account: AccountModel = c.get(OwnTransferControlNames.SRC_ACCOUNT).value;
        const amount = c.get(OwnTransferControlNames.AMOUNT);

        let previousErrors = amount.errors;
        if (account.currentBalance - amount.value < 0) {
            amount.setErrors({ amountExceedsBalance: true });
        } else {
            amount.setErrors(previousErrors);
        }
        return null;
    }
}