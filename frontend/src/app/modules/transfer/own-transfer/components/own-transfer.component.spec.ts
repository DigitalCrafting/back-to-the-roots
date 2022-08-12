import {ComponentFixture, TestBed} from '@angular/core/testing';

import {OwnTransferComponent} from './own-transfer.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {EregoldUserContext} from "../../../../context/eregold-user-context";
import {AccountModel} from "../../../../models/account.models";
import {By} from "@angular/platform-browser";
import {TransactionModel, TransactionsService} from "../../../../services/transactions.service";
import {Observable, of} from "rxjs";
import {AccountTypeEnum, CurrencyEnum} from "../../../../models/enums";

describe('OwnTransferComponent', () => {
    let component: OwnTransferComponent;
    let fixture: ComponentFixture<OwnTransferComponent>;
    let eregoldUserContext: EregoldUserContext;
    let transactionsService: TransactionsService;

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

        transactionsService = {
            transfer(request: TransactionModel): Observable<any> {
                return of(true);
            }
        } as TransactionsService;

        await TestBed.configureTestingModule({
            imports: [
                HttpClientModule,
                FormsModule,
                ReactiveFormsModule,
                CommonModule
            ],
            declarations: [OwnTransferComponent],
            providers: [
                {provide: EregoldUserContext, useValue: eregoldUserContext},
                {provide: TransactionsService, useValue: transactionsService}
            ]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(OwnTransferComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should init form group and limit dstAccountList', async () => {
        component.setContext('12ERGD12345')
        await component.ngOnInit();

        expect(component.srcAccountList).toBeTruthy();
        expect(component.dstAccountList).toBeTruthy();
        expect(component.dstAccountList.length).toEqual(1);
        expect(component.dstAccountList[0].accountNumber).toEqual('12ERGD67890')
    });

    it('should have "transfer" button disabled initially', () => {
        const transferButton = fixture.debugElement.query(By.css('#transferButton'));
        expect(transferButton.nativeElement.disabled).toBeTruthy();
    });

    describe("when the form is filled", async () => {
        beforeEach(async () => {
            component.setContext('12ERGD12345')
            await component.ngOnInit();

            component.formGroup.patchValue({
                dstAccount: {
                    accountNumber: '12ERGD67890',
                    accountName: 'Test name',
                    currentBalance: 90.0,
                    currency: CurrencyEnum.GLD,
                    type: AccountTypeEnum.SAVING
                } as AccountModel,
                description: 'Test',
                amount: 10
            });

            fixture.detectChanges();
        })

        it('should have "transfer" button enabled', async () => {
            const transferButton = fixture.debugElement.query(By.css('#transferButton'));
            expect(transferButton.nativeElement.disabled).toBeFalsy();
        });

        it('should call transaction api on "transfer" click', async () => {
            const spy = spyOn(transactionsService, 'transfer').and.returnValue(of(true));

            const transferButton = fixture.debugElement.query(By.css('#transferButton'));
            transferButton.nativeElement.click();

            expect(spy).toHaveBeenCalledWith({
                srcAccount: '12ERGD12345',
                dstAccount: '12ERGD67890',
                description: 'Test',
                amount: 10,
                currency: CurrencyEnum.GLD
            });
        });
    })

    it('should emit event on \"cancel\" click', () => {
        const spy = spyOn(component.backEventEmitter, 'emit');
        const cancelButton = fixture.debugElement.query(By.css('#transferCancelButton'));

        cancelButton.nativeElement.click();
        expect(spy).toHaveBeenCalled();
    });
});
