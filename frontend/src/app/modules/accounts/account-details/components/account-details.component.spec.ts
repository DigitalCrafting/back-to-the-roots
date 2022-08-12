import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AccountDetailsComponent} from './account-details.component';
import {HttpClientModule} from "@angular/common/http";
import {By} from "@angular/platform-browser";
import {TransactionsModule} from "../../../transactions/transactions.module";
import {AccountsService} from "../../../../services/accounts.service";

describe('AccountDetailsComponent', () => {
    let component: AccountDetailsComponent;
    let fixture: ComponentFixture<AccountDetailsComponent>;

    let accountsService: AccountsService;

    beforeEach(async () => {
        accountsService = {
            getAccountDetails(accountNumber: string): Promise<any> {
                return Promise.resolve(true);
            }
        } as AccountsService;

        await TestBed.configureTestingModule({
            declarations: [AccountDetailsComponent],
            imports: [
                HttpClientModule,
                TransactionsModule
            ],
            providers: [
                {provide: AccountsService, useValue: accountsService}
            ]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(AccountDetailsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should call back event emitter on "Back" click', () => {
        const spy = spyOn(component.backToDetailsEventEmitter, 'emit');

        const button = fixture.debugElement.query(By.css('#backButton'));
        button.nativeElement.click();

        expect(spy).toHaveBeenCalled();
    });

    it('should call own transfer event emitter on "Own transfer" click', () => {
        const spy = spyOn(component.makeOwnTransferEventEmitter, 'emit');

        const button = fixture.debugElement.query(By.css('#ownTransferButton'));
        button.nativeElement.click();

        expect(spy).toHaveBeenCalled();
    });
});
