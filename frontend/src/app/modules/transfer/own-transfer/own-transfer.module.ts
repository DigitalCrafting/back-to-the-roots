import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {OwnTransferComponent} from './components/own-transfer.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
    imports: [
        FormsModule,
        CommonModule,
        ReactiveFormsModule
    ],
    exports: [
        OwnTransferComponent
    ],
    declarations: [
        OwnTransferComponent
    ]
})
export class OwnTransferModule {
}
