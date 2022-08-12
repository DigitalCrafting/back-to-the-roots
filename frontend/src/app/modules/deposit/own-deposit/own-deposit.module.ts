import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {OwnDepositComponent} from './components/own-deposit.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
    ],
    exports: [
        OwnDepositComponent
    ],
    declarations: [
        OwnDepositComponent
    ]
})
export class OwnDepositModule {
}
