import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {OwnDepositModule} from "./own-deposit/own-deposit.module";

@NgModule({
    declarations: [],
    imports: [
        CommonModule,
        OwnDepositModule
    ],
    exports: [
        OwnDepositModule
    ]
})
export class DepositModule {
}
