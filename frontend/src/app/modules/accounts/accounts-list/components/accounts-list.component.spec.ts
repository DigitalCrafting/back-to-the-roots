import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AccountsListComponent} from './accounts-list.component';
import {By} from "@angular/platform-browser";
import {AccountModel} from "../../../../models/account.models";
import {EregoldUserContext} from "../../../../context/eregold-user-context";
import {AccountTypeEnum, CurrencyEnum} from "../../../../models/enums";

describe('AccountsListComponent', () => {
    let component: AccountsListComponent;
    let fixture: ComponentFixture<AccountsListComponent>;
    let eregoldUserContext: EregoldUserContext;

    let mockAccountsList = Promise.resolve([
        {
            accountNumber: '12ERGD12345',
            accountName: 'Test name',
            currentBalance: 12.0,
            currency: CurrencyEnum.GLD,
            type: AccountTypeEnum.DEBIT
        } as AccountModel,
        {
            accountNumber: '12ERGD67890',
            accountName: 'Test name',
            currentBalance: 90.0,
            currency: CurrencyEnum.GLD,
            type: AccountTypeEnum.SAVING
        } as AccountModel
    ]);

    beforeEach(async () => {
        eregoldUserContext = {
            async getAccounts(): Promise<Array<AccountModel>> {
                return mockAccountsList;
            }
        } as EregoldUserContext;

        await TestBed.configureTestingModule({
            declarations: [AccountsListComponent],
            providers: [
                {provide: EregoldUserContext, useValue: eregoldUserContext}
            ]
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(AccountsListComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should show 2 accounts', async () => {
        await component.ngOnInit();

        expect(component.accountsList).toBeTruthy();
        expect(component.accountsList.length).toEqual(2);

        fixture.detectChanges();

        const rows = fixture.debugElement.queryAll(By.css('table > tbody > tr'));
        expect(rows.length).toEqual(2);
    });

    it('should emit event on \"add account\" click', () => {
        const spy = spyOn(component.createAccountEventEmitter, 'emit');
        const addAccountButton = fixture.debugElement.query(By.css('#addAccountButton'));

        addAccountButton.nativeElement.click();
        expect(spy).toHaveBeenCalled();
    });
});
