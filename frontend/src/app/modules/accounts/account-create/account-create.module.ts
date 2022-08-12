import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AccountCreateComponent} from './components/account-create.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
    declarations: [
        AccountCreateComponent
    ],
    imports: [
        FormsModule,
        CommonModule,
        ReactiveFormsModule
    ]
})
export class AccountCreateModule {
}
