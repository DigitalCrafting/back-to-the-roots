import {fakeAsync, TestBed} from "@angular/core/testing";
import {HttpClientModule} from "@angular/common/http";
import {EregoldUserContext} from "./eregold-user-context";
import {AccountsService} from "../services/accounts.service";
import {AccountModel} from "../models/account.models";
import {AccountTypeEnum, CurrencyEnum} from "../models/enums";

describe('EregoldUserContext', () => {
    let context: EregoldUserContext;
    let accountsService: jasmine.SpyObj<AccountsService>;

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

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [
                HttpClientModule
            ],
            providers: [
                {provide: AccountsService, useValue: jasmine.createSpyObj('AccountsService', ['getAccounts'])}
            ]
        });
        context = TestBed.inject(EregoldUserContext);
        // @ts-ignore
        accountsService = TestBed.inject(AccountsService);
    });

    it('should be created', () => {
        expect(context).toBeTruthy();
        expect(accountsService).toBeTruthy();
    });

    it('should call getAccounts once and store the result', fakeAsync(async () => {
            accountsService.getAccounts.and.returnValue(mockAccountsList);

            let accountList = await context.getAccounts()
            expect(accountList.length).toEqual(2);
            expect(accountsService.getAccounts).toHaveBeenCalled();

            accountsService.getAccounts.calls.reset();
            accountList = await context.getAccounts()
            expect(accountList.length).toEqual(2);
            expect(accountsService.getAccounts).toHaveBeenCalledTimes(0);
        })
    );
});
