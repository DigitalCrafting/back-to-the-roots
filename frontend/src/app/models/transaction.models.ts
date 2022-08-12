import {CurrencyEnum, TransactionTypeEnum} from "./enums";

export interface TransactionModel {
    id: number;
    accountNumber: string;
    foreignAccountNumber: string;
    description: string;
    type: TransactionTypeEnum;
    currency: CurrencyEnum;
    amount: number;
    date: Date;
}