import {TestBed} from '@angular/core/testing';

import {AccountsService} from './accounts.service';
import {HttpClientModule} from "@angular/common/http";

describe('AccountsService', () => {
    let service: AccountsService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [
                HttpClientModule
            ]
        });
        service = TestBed.inject(AccountsService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
