import {AbstractControl} from "@angular/forms";

export namespace EregoldRegisterValidators {
    export function PasswordGroupValidator(c: AbstractControl): any {
        const p1 = c.get('password');
        const p2 = c.get('passwordCheck');

        let previousErrors = p2.errors;
        if (p2.value && p1.value && p1.value === p2.value) {
            p2.setErrors(previousErrors);
        } else if (p2.errors === null) {
            p2.setErrors({
                passwordDoesNotMatch: true
            });
        }
        return null;
    }
}