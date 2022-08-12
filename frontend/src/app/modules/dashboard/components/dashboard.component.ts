import {AfterViewInit, Component, OnDestroy, ViewChild} from '@angular/core';
import {UserContext} from "../../common/user.context";
import {Router} from "@angular/router";
import {DynamicComponentManager} from "../../../core/dynamic-component-manager/dynamic-component-manager";
import {AccountsListComponent} from "../../accounts/accounts-list/components/accounts-list.component";
import {Subscription} from "rxjs";
import {
    AccountCreateAction,
    AccountCreateComponent
} from "../../accounts/account-create/components/account-create.component";
import {EregoldRoutes} from "../../../utils/routes.enum";
import {AccountDetailsComponent} from "../../accounts/account-details/components/account-details.component";
import {OwnTransferComponent} from "../../transfer/own-transfer/components/own-transfer.component";
import {OwnDepositComponent} from "../../deposit/own-deposit/components/own-deposit.component";

enum DashboardPage {
    ACCOUNTS_LIST = 'ACCOUNTS_LIST',
    ACCOUNT_DETAILS = 'ACCOUNT_DETAILS',
    ACCOUNT_CREATE = 'ACCOUNT_CREATE',
    OWN_TRANSFER = 'OWN_TRANSFER',
    OWN_DEPOSIT = 'OWN_TRANSFER'
}

@Component({
    selector: 'dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements AfterViewInit, OnDestroy {

    @ViewChild('componentManager')
    componentManager: DynamicComponentManager;

    accountsListComponent: AccountsListComponent;
    createAccountSubscription: Subscription;
    showDetailsSubscription: Subscription;

    accountCreateComponent: AccountCreateComponent;
    accountCreateEventSubscription: Subscription;

    accountDetailsComponent: AccountDetailsComponent;
    backToListSubscription: Subscription;
    makeOwnTransferSubscription: Subscription;
    makeOwnDepositSubscription: Subscription;

    ownTransferComponent: OwnTransferComponent;
    ownTransferBackSubscription: Subscription;

    ownDepositComponent: OwnDepositComponent;
    ownDepositBackSubscription: Subscription;

    constructor(private _userContext: UserContext,
                private _router: Router) {
    }

    ngAfterViewInit() {
        if (!this._userContext.isLoggedIn) {
            this._router.navigate([EregoldRoutes.LOGIN]);
        } else {
            this.showAccountsList();
        }
    }

    ngOnDestroy() {
        this.cleanUp();
    }

    private showAccountsList() {
        this.cleanUp();
        this.accountsListComponent = this.componentManager.show(AccountsListComponent);
        this.createAccountSubscription = this.accountsListComponent.createAccountEventEmitter.subscribe(() => {
            this.showAccountCreate();
        });
        this.showDetailsSubscription = this.accountsListComponent.showDetailsEventEmitter.subscribe(accountNumber => {
            this.showAccountDetails(accountNumber);
        })
        this.makeOwnTransferSubscription = this.accountsListComponent.makeOwnTransferEventEmitter.subscribe(() => {
            this.showOwnTransfer(DashboardPage.ACCOUNTS_LIST);
        });
        this.makeOwnDepositSubscription = this.accountsListComponent.makeOwnDepositEventEmitter.subscribe(() => {
            this.showOwnDeposit(DashboardPage.ACCOUNTS_LIST);
        })
    }

    private showAccountCreate() {
        this.cleanUp();
        this.accountCreateComponent = this.componentManager.show(AccountCreateComponent);
        this.accountCreateEventSubscription = this.accountCreateComponent.accountCreateEventEmitter.subscribe((action: AccountCreateAction) => {
            if (action === AccountCreateAction.SHOW_LIST) {
                this.showAccountsList();
            }
        });
    }

    private showAccountDetails(accountNumber: string) {
        this.cleanUp();
        this.accountDetailsComponent = this.componentManager.show(AccountDetailsComponent, accountNumber);
        this.backToListSubscription = this.accountDetailsComponent.backToDetailsEventEmitter.subscribe(() => {
            this.showAccountsList();
        })
        this.makeOwnTransferSubscription = this.accountDetailsComponent.makeOwnTransferEventEmitter.subscribe(() => {
            this.showOwnTransfer(DashboardPage.ACCOUNT_DETAILS, accountNumber);
        });
        this.makeOwnDepositSubscription = this.accountDetailsComponent.makeOwnDepositEventEmitter.subscribe(() => {
            this.showOwnDeposit(DashboardPage.ACCOUNT_DETAILS, accountNumber);
        });
    }

    private showOwnTransfer(backTo: DashboardPage, accountNumber?: string) {
        this.cleanUp();
        this.ownTransferComponent = this.componentManager.show(OwnTransferComponent, accountNumber);
        this.ownTransferBackSubscription = this.ownTransferComponent.backEventEmitter.subscribe(() => {
            if (backTo === DashboardPage.ACCOUNTS_LIST) {
                this.showAccountsList();
            } else if (backTo === DashboardPage.ACCOUNT_DETAILS) {
                this.showAccountDetails(accountNumber);
            }
        })
    }

    private showOwnDeposit(backTo: DashboardPage, accountNumber?: string) {
        this.cleanUp();
        this.ownDepositComponent = this.componentManager.show(OwnDepositComponent, accountNumber);
        this.ownDepositBackSubscription = this.ownDepositComponent.backEventEmitter.subscribe(() => {
            if (backTo === DashboardPage.ACCOUNTS_LIST) {
                this.showAccountsList();
            } else if (backTo === DashboardPage.ACCOUNT_DETAILS) {
                this.showAccountDetails(accountNumber);
            }
        })
    }

    private cleanUp() {
        this.showDetailsSubscription?.unsubscribe();
        delete this.showDetailsSubscription;
        this.createAccountSubscription?.unsubscribe();
        delete this.createAccountSubscription;
        this.accountCreateEventSubscription?.unsubscribe();
        delete this.accountCreateEventSubscription;
        this.backToListSubscription?.unsubscribe();
        delete this.backToListSubscription;
        this.makeOwnTransferSubscription?.unsubscribe();
        delete this.makeOwnTransferSubscription;
        this.makeOwnDepositSubscription?.unsubscribe();
        delete this.makeOwnDepositSubscription;
        this.ownTransferBackSubscription?.unsubscribe();
        delete this.ownTransferBackSubscription;
        this.ownDepositBackSubscription?.unsubscribe();
        delete this.ownDepositBackSubscription;

        delete this.accountCreateComponent;
        delete this.accountsListComponent;
        delete this.accountDetailsComponent;
        delete this.ownTransferComponent;
        delete this.ownDepositComponent;
    }
}
