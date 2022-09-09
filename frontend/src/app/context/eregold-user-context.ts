import {Injectable} from "@angular/core";
import {AccountModel} from "../models/account.models";
import {AccountsService} from "../services/accounts.service";
import {ServiceDispatcher} from "../core/service-dispatcher/service-dispatcher";

@Injectable({
    providedIn: "root"
})
export class EregoldUserContext {
    private _accounts: Array<AccountModel>;

    constructor(private _accountsService: AccountsService) {
    }

    async getAccounts(reload = false): Promise<Array<AccountModel>> {
        if (!this._accounts || reload) {
            this._accounts = await ServiceDispatcher.dispatch(this._accountsService.getAccounts());
        }
        return this._accounts;
    }
}