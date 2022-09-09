import {Component, EventEmitter, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";

@Component({
  selector: 'loading-spinner',
  templateUrl: './loading-spinner.component.html',
  styleUrls: ['./loading-spinner.component.scss']
})
export class LoadingSpinnerComponent implements OnInit, OnDestroy {

  public static TOGGLE_SPINNER: EventEmitter<boolean> = new EventEmitter<boolean>();
  public showSpinner: boolean = false;
  private toggleSubscription: Subscription;

  ngOnInit(): void {
    this.toggleSubscription = LoadingSpinnerComponent.TOGGLE_SPINNER.subscribe((show) => {
      this.showSpinner = show;
    });
  }

  ngOnDestroy() {
    this.toggleSubscription?.unsubscribe();
    delete this.toggleSubscription;
  }
}
