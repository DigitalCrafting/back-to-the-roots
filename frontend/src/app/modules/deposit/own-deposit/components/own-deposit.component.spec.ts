import {ComponentFixture, TestBed} from '@angular/core/testing';

import {OwnDepositComponent} from './own-deposit.component';
import {EregoldUserContext} from "../../../../context/eregold-user-context";
import {TransactionModel, TransactionsService} from "../../../../services/transactions.service";
import {AccountTypeEnum, CurrencyEnum} from "../../../../models/enums";
import {AccountModel} from "../../../../models/account.models";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {By} from "@angular/platform-browser";

describe('OwnDepositComponent', () => {
  let component: OwnDepositComponent;
  let fixture: ComponentFixture<OwnDepositComponent>;
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
      deposit(request: TransactionModel): Promise<any> {
        return Promise.resolve(true);
      }
    } as TransactionsService;

    await TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        CommonModule
      ],
      declarations: [OwnDepositComponent],
      providers: [
        {provide: EregoldUserContext, useValue: eregoldUserContext},
        {provide: TransactionsService, useValue: transactionsService}
      ]
    })
        .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OwnDepositComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should init form group', async () => {
    component.setContext('12ERGD12345')
    await component.ngOnInit();

    expect(component.dstAccountList).toBeTruthy();
    expect(component.dstAccountList[0].accountNumber).toEqual('12ERGD12345')
  });

  it('should have "deposit" button disabled initially', () => {
    const transferButton = fixture.debugElement.query(By.css('#depositButton'));
    expect(transferButton.nativeElement.disabled).toBeTruthy();
  });

  describe("when the form is filled", async () => {
    beforeEach(async () => {
      component.setContext('12ERGD12345')
      await component.ngOnInit();

      component.formGroup.patchValue({
        dstAccount: '12ERGD12345',
        description: 'Test',
        amount: 10
      });

      fixture.detectChanges();
    })

    it('should have "deposit" button enabled', async () => {
      const transferButton = fixture.debugElement.query(By.css('#depositButton'));
      expect(transferButton.nativeElement.disabled).toBeFalsy();
    });

    it('should call transaction api on "deposit" click', async () => {
      const spy = spyOn(transactionsService, 'deposit').and.returnValue(Promise.resolve(true));

      const transferButton = fixture.debugElement.query(By.css('#depositButton'));
      transferButton.nativeElement.click();

      expect(spy).toHaveBeenCalledWith({
        dstAccount: '12ERGD12345',
        description: 'Test',
        amount: 10,
        currency: CurrencyEnum.GLD
      });
    });
  })

  it('should emit event on \"cancel\" click', () => {
    const spy = spyOn(component.backEventEmitter, 'emit');
    const cancelButton = fixture.debugElement.query(By.css('#depositCancelButton'));

    cancelButton.nativeElement.click();
    expect(spy).toHaveBeenCalled();
  });
});
