import {Component, HostListener} from '@angular/core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {
    title = 'eregold-ui';

    @HostListener("window:onbeforeunload", ["$event"])
    clearLocalStorage(event: any) {
        localStorage.clear();
    }
}
