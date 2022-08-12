import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LoginComponent} from './login.component';
import {LoginService} from "../../../services/login.service";
import {Router} from "@angular/router";
import {By} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

describe('LoginComponent', () => {
    let component: LoginComponent;
    let fixture: ComponentFixture<LoginComponent>;

    let loginService: LoginService;

    beforeEach(async () => {
        loginService = new LoginService(null);
        await TestBed.configureTestingModule({
            declarations: [LoginComponent],
            providers: [
                {provide: LoginService, useValue: loginService},
                {provide: Router, useValue: {}},
            ],
            imports: [
                ReactiveFormsModule,
                FormsModule
            ]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(LoginComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should have login button disabled initially', () => {
        const registerButton = fixture.debugElement.query(By.css('#loginButton'));

        expect(registerButton.nativeElement.disabled).toBeTruthy();
    })

    it('should have login button enabled when form is filled', () => {
        component.formGroup.get('password').setValue("verystrongpass");
        component.formGroup.get('userId').setValue("test");

        fixture.detectChanges(true);

        const loginButton = fixture.debugElement.query(By.css('#loginButton'));
        expect(loginButton.nativeElement.disabled).toBeFalsy();
    })
});
