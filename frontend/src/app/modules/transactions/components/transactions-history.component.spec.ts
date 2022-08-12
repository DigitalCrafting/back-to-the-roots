import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TransactionsHistoryComponent} from './transactions-history.component';
import {TransactionModel} from "../../../models/transaction.models";
import {CurrencyEnum, TransactionTypeEnum} from "../../../models/enums";
import {By} from "@angular/platform-browser";

describe('TransactionsHistoryComponent', () => {
    let component: TransactionsHistoryComponent;
    let fixture: ComponentFixture<TransactionsHistoryComponent>;

    let testTransactionsList: Array<TransactionModel> = [
        {
            id: 1,
            accountNumber: "12ERGD12345",
            foreignAccountNumber: "12ERGD67890",
            type: TransactionTypeEnum.TRANSFER,
            amount: 500,
            currency: CurrencyEnum.GLD,
            date: new Date(),
            description: "Test transaction 1"
        } as TransactionModel,
        {
            id: 2,
            accountNumber: "12ERGD12345",
            foreignAccountNumber: "12ERGD67890",
            type: TransactionTypeEnum.TRANSFER,
            amount: 600,
            currency: CurrencyEnum.GLD,
            date: new Date(),
            description: "Test transaction 2"

        } as TransactionModel,
    ]

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [TransactionsHistoryComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(TransactionsHistoryComponent);
        component = fixture.componentInstance;
        component.transactionsHistory = testTransactionsList;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should display table with 2 rows', () => {
        const rows = fixture.debugElement.queryAll(By.css('table > tbody > tr'));
        expect(rows.length).toEqual(2);
    });
});
