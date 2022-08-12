import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AccountCreateComponent} from './account-create.component';
import {AccountsService} from "../../../../services/accounts.service";
import {Observable, of} from "rxjs";
import {By} from "@angular/platform-browser";
import {CreateAccountRequest} from "../../../../models/account.models";
import {EregoldUserContext} from "../../../../context/eregold-user-context";

describe('AccountCreateComponent', () => {
    let component: AccountCreateComponent;
    let fixture: ComponentFixture<AccountCreateComponent>;

    let accountsService: AccountsService;
    let userContext: EregoldUserContext;

    beforeEach(async () => {
        accountsService = {
            createAccount(request: CreateAccountRequest): Observable<Object> {
                return of(true);
            }
        } as AccountsService;
        userContext = {
            async getAccounts(reload: boolean = false): Promise<any> {
                return of(true);
            }
        } as EregoldUserContext;
        await TestBed.configureTestingModule({
            declarations: [AccountCreateComponent],
            providers: [
                {provide: AccountsService, useValue: accountsService},
                {provide: EregoldUserContext, useValue: userContext}
            ]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(AccountCreateComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should call create account with default values on click', () => {
        // @ts-ignore
        const spy = spyOn(component._accountsService, 'createAccount');

        const addAccountButton = fixture.debugElement.query(By.css('#addAccountButton'));
        addAccountButton.nativeElement.click();

        expect(spy).toHaveBeenCalledWith(component.formGroup.value);
    })
});
