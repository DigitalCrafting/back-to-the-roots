import {AccountTypeEnum, CurrencyEnum} from "./enums";
import {TransactionModel} from "./transaction.models";

export interface AccountModel {
    accountNumber: string;
    accountName: string;
    currentBalance: number;
    currency: CurrencyEnum;
    type: AccountTypeEnum;
}

export interface CreateAccountRequest {
    accountType: AccountTypeEnum;
    currency: CurrencyEnum;
}

export interface AccountDetailsModel {
    accountNumber: string;
    accountName: string;
    currentBalance: number;
    currency: CurrencyEnum;
    type: AccountTypeEnum;
    transactionsList: Array<TransactionModel>;
}