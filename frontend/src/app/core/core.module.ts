import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DynamicComponentManager} from './dynamic-component-manager/dynamic-component-manager';

@NgModule({
    declarations: [
        DynamicComponentManager
    ],
    exports: [
        DynamicComponentManager
    ],
    imports: [
        CommonModule
    ]
})
export class CoreModule {
}
