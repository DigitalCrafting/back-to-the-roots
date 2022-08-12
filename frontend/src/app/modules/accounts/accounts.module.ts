import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AccountsListModule} from "./accounts-list/accounts-list.module";
import {AccountCreateModule} from "./account-create/account-create.module";
import {AccountDetailsModule} from "./account-details/account-details.module";


@NgModule({
    imports: [
        CommonModule,
        AccountsListModule,
        AccountCreateModule,
        AccountDetailsModule
    ],
    exports: [
        CommonModule,
        AccountsListModule,
        AccountCreateModule,
        AccountDetailsModule
    ],
    declarations: [],
})
export class AccountsModule {
}
