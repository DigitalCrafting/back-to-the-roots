import {Component, ComponentFactoryResolver, ComponentRef, OnDestroy, ViewChild, ViewContainerRef} from "@angular/core";
import {ObjectsUtils} from "../utils/eregold-utils";

@Component({
    selector: 'dynamic-component-manager',
    template: `
        <ng-template #container></ng-template>`
})
export class DynamicComponentManager implements OnDestroy {
    // @ts-ignore
    @ViewChild('container', {read: ViewContainerRef}) container;

    // @ts-ignore
    private _cmpRef: ComponentRef<any>;

    constructor(private resolver: ComponentFactoryResolver) {

    }

    public show(componentType: any, context?: any): any {
        if (!ObjectsUtils.isNullOrUndefined(this._cmpRef)) {
            this._cmpRef.destroy();
        }

        const fact = this.resolver.resolveComponentFactory(componentType);
        this._cmpRef = this.container.createComponent(fact);
        let instance = this._cmpRef.instance;

        /*
        * Big advantage of this approach is that 'setContext' method will be called
        * AFTER the constructor (so the services are there) but BEFORE the ngOnInit,
        * so you don't have rely on the @Input property anymore,
        * you will be 100% sure that the context is there before the angular lifecycle methods.
        * */
        if (instance.setContext && context) {
            instance.setContext(context);
        }

        return instance;
    }

    ngOnDestroy() {
        if (!ObjectsUtils.isNullOrUndefined(this._cmpRef)) {
            this._cmpRef.destroy();
        }
    }
}
