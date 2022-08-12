import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from './components/dashboard.component';
import {RouterModule, Routes} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AccountsModule} from "../accounts/accounts.module";
import {CoreModule} from "../../core/core.module";
import {TopBarModule} from "../top-bar/top-bar.module";
import {TransferModule} from "../transfer/transfer.module";
import {DepositModule} from "../deposit/deposit.module";

const routes: Routes = [
    {
        path: '',
        component: DashboardComponent
    }
];

@NgModule({
    imports: [
        HttpClientModule,
        FormsModule,
        CommonModule,
        ReactiveFormsModule,
        RouterModule.forChild(routes),

        AccountsModule,
        TransferModule,
        DepositModule,
        TopBarModule,
        CoreModule
    ],
    declarations: [
        DashboardComponent,
    ],
})
export class DashboardModule {
}
