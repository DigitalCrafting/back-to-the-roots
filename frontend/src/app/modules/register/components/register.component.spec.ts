import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RegisterComponent} from './register.component';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {Router, RouterModule} from "@angular/router";
import {RegisterService} from "../../../services/register.service";
import {By} from "@angular/platform-browser";

describe('RegisterComponent', () => {
    let component: RegisterComponent;
    let fixture: ComponentFixture<RegisterComponent>;

    let httpClientSpy: jasmine.SpyObj<HttpClient>;
    let registerService: RegisterService;

    beforeEach(async () => {
        registerService = new RegisterService(null);

        await TestBed.configureTestingModule({
            declarations: [RegisterComponent],
            imports: [
                HttpClientModule,
                FormsModule,
                CommonModule,
                ReactiveFormsModule,
                RouterModule
            ],
            providers: [
                {provide: RegisterService, useValue: registerService},
                {provide: Router, useValue: {}},
            ]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(RegisterComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should have register button disabled initially', () => {
        const registerButton = fixture.debugElement.query(By.css('#registerButton'));

        expect(registerButton.nativeElement.disabled).toBeTruthy();
    })

    it('should have register button enabled when form is filled', () => {
        component.formGroup.get('email').setValue("test@test.com");
        component.formGroup.get('firstName').setValue("Test");
        component.formGroup.get('lastName').setValue("Testowy");
        component.formGroup.get('password').setValue("verystrongpass");
        component.formGroup.get('passwordCheck').setValue("verystrongpass");

        fixture.detectChanges(true);

        const registerButton = fixture.debugElement.query(By.css('#registerButton'));
        expect(registerButton.nativeElement.disabled).toBeFalsy();
    })
});
