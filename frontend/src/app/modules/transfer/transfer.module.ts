import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {OwnTransferModule} from "./own-transfer/own-transfer.module";

@NgModule({
    imports: [
        CommonModule,
        OwnTransferModule
    ],
    exports: [
        OwnTransferModule
    ]
})
export class TransferModule {
}
