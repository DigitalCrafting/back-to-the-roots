import {FormControl, FormGroup} from "@angular/forms";
import {EregoldRegisterValidators} from "./register.validators";

describe('Register validators', () => {
    describe('PasswordGroupValidator', () => {
        let passwordFormControl = new FormControl();
        let passwordCheckFormControl = new FormControl();

        let formGroup: FormGroup = new FormGroup({
            password: passwordFormControl,
            passwordCheck: passwordCheckFormControl
        }, EregoldRegisterValidators.PasswordGroupValidator);

        it('should set form group to invalid when password and passwordCheck does not match', () => {
            // when
            passwordFormControl.setValue('test');
            passwordCheckFormControl.setValue('differentTest');

            // then
            expect(formGroup.invalid).toBeTruthy();
        });

        it('should set form group to valid when password and passwordCheck match', () => {
            // when
            passwordFormControl.setValue('test');
            passwordCheckFormControl.setValue('test');

            // then
            expect(formGroup.valid).toBeTruthy();
        });

        it('should preserve previous errors', () => {
            // given
            let expectedError = {myError: true};

            // when
            passwordFormControl.setValue('test');
            passwordCheckFormControl.setValue('test');
            passwordCheckFormControl.setErrors(expectedError);

            // then
            expect(passwordCheckFormControl.hasError('myError')).toBeTruthy();
        })
    });

});