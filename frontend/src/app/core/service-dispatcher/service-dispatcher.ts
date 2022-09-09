import {LoadingSpinnerComponent} from "../loading-spinner/loading-spinner.component";

export class ServiceDispatcher {
    public static dispatch<Type>($call: Promise<any>): Promise<Type> {
        return new Promise((resolve, reject) => {
            LoadingSpinnerComponent.TOGGLE_SPINNER.emit(true);
            $call.then(response => {
                resolve(response);
            }).catch(error => {
                reject(error);
            }).finally(() => {
                LoadingSpinnerComponent.TOGGLE_SPINNER.emit(false);
            })
        })
    }
}