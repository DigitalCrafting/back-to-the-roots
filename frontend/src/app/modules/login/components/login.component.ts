import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginRequest, LoginResponse, LoginService} from "../../../services/login.service";
import {UserContext} from "../../common/user.context";
import {Router} from "@angular/router";
import {EregoldRoutes} from "../../../utils/routes.enum";

@Component({
    selector: 'login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    formGroup: FormGroup = new FormGroup({
        userId: new FormControl('', Validators.required),
        password: new FormControl('', Validators.required),
    });

    constructor(private _loginService: LoginService,
                private _userContext: UserContext,
                private _router: Router) {
    }

    ngOnInit() {
        if (this._userContext.isLoggedIn) {
            this._router.navigate([EregoldRoutes.UI])
        }
    }

    async onLoginClicked() {
        if (this.formGroup.valid) {
            let pass = this.formGroup.get('password').value;
            let userId = this.formGroup.get('userId').value;

            let request: LoginRequest = <LoginRequest>{
                userId: userId,
                password: [...pass]
            }

            this._loginService.login(request).subscribe(
                (resp: LoginResponse) => {
                    sessionStorage.setItem("token", resp.token);
                    this._userContext.isLoggedIn = true;
                    this._router.navigate([EregoldRoutes.UI]);
                },
                (error) => {
                    // TODO message for user
                    console.log("Invalid credentials", error)
                });
        }
    }

    onRegisterClicked() {
        this._router.navigate([EregoldRoutes.REGISTER]);
    }
}
