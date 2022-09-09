import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DynamicComponentManager} from './dynamic-component-manager/dynamic-component-manager';
import { LoadingSpinnerComponent } from './loading-spinner/loading-spinner.component';

@NgModule({
    declarations: [
        DynamicComponentManager,
        LoadingSpinnerComponent
    ],
    exports: [
        DynamicComponentManager,
        LoadingSpinnerComponent
    ],
    imports: [
        CommonModule
    ]
})
export class CoreModule {
}
