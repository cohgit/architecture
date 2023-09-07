import {Component, EventEmitter, Input, OnInit, Output,} from '@angular/core';
import {Clients} from '../../../interfaces/IClients';
import {MatHorizontalStepper} from '@angular/material/stepper';
import {TermsComponent} from './terms/terms.component';
import {MatDialog} from '@angular/material/dialog';
import {UtilService} from '../../../helpers/util.service';
import {TranslateHelperService} from '../../../helpers/translate-helper.service';
import {PaymentStripeService} from '../../../services/payment-stripe.service';
import {SuscriptionService} from '../../../services/suscription.service';
import {SessionService} from 'src/app/helpers/session.service';
import {ModalService} from "../../../helpers/modal.service";

@Component({
  selector: 'app-new-account',
  templateUrl: './new-account.component.html',
  styleUrls: ['./new-account.component.css'],
})
export class NewAccountComponent implements OnInit {
  @Input() plan: any;
  @Input() suggestion: any;
  @Input() stepperCheckout: MatHorizontalStepper;
  @Input() params: any;
  @Input() preSelected: any;

  @Output() paymentResult = new EventEmitter<any>();

  client = new Clients();
  terms = false;
  pass: any;
  confirmPass: any;
  duplicatedEmail: any;
  payment = 'transfer';
  isTrue = true;
  isFalse = false;
  disabledEmail = false;
  hide = true;

  /**
   * create a new account for the client
   * @param dialog
   * @param utilService
   * @param translate
   * @param suscriptionService
   * @param paymentStripeService
   * @param sessionService
   * @param modal
   */
  constructor(
    private dialog: MatDialog,
    public utilService: UtilService,
    private translate: TranslateHelperService,
    private suscriptionService: SuscriptionService,
    private paymentStripeService: PaymentStripeService,
    private sessionService: SessionService,
    private modal: ModalService
  ) {
  }

  ngOnInit(): void {
    if (this.suggestion?.plan?.customized) {
      this.plan = this.suggestion.plan;
      this.client.email = this.suggestion.client_email;
      this.disabledEmail = true;
    }
  }

  /**
   * allows to change plans
   * DEPRECATED you can´t  change plan
   */
  changePlan() {
    if (!this.preSelected) {
      this.stepperCheckout.previous();
    } else {
      this.modal.openConfirmation({
        message: 'message.confirmation.change_plan',
        onConfirm: () => {
          this.sessionService.goTo('/client/checkout');
        }
      });
    }
  }

  /**
   * open a modal
   */
  openModal(): void {
    this.dialog.open(TermsComponent, {
      disableClose: true,
      closeOnNavigation: true,
    });
  }

  /**
   *used to pay a plan
   * @param plan
   * @param email
   */
  async checkout(plan, email) {
    if (plan.automatic_renewal) {
      this.paymentStripeService
        .checkoutSubscription(email, plan.stripeKey.key_payment)
        .then(
          (r) => console.log('success: ', r),
          (err) => {
            this.utilService.showNotification('error.stripe_payment', 'danger');
            console.error('error: ', err)
          }
        );
    } else {
      this.paymentStripeService
        .checkoutOneTime(email, plan.stripeKey.key_payment)
        .then(
          (r) => console.log('success: ', r),
          (err) => {
            this.utilService.showNotification('error.stripe_payment', 'danger');
            console.error('error: ', err)
          }
        );
    }
  }

  /**
   * Check if the mail that the client is writing a new user has been registered in the database
   * @param email
   */
  checkEmailDuplicated(email) {
    this.suscriptionService.verifyNewEmailClient(
      email,
      (response) => (this.duplicatedEmail = response)
    );
  }

  /**
   * In case the payment is by stripe, create a session, before proceeding to the payment
   * @param client
   */
  createSessionReference(suscription: any): void {
    this.sessionService.saveTempSuscription(suscription);
  }

  /**
   * pays a plan
   * @param form
   */
  pay(form: any, typePayment?: any): void {
    if (form.value.terms) {
      if (this.payment || this.payment !== '') {
        if (form.valid) {
          const suscription = {client: form.value, plan: this.plan};

          if (typePayment === 'stripe') {
            this.createSessionReference(suscription);
            // Stripe Methods
            this.suscriptionService.attemptSuscribe(suscription, () => {
              '';
              this.checkout(this.plan, form.value.email).then(
                (r) => console.log('success: ', r),
                (err) => console.error('error: ', err)
              );
            });
          }
          if (typePayment === 'transfer') {
            this.suscriptionService.suscribeAsTransfer(suscription, () => {
              this.sessionService.goTo('/client/check/finalTransfer');
            })
          }
          if (typePayment === 'demo') {
            // Transfer Methods
            this.suscriptionService.suscribeAsDemo(suscription, () => {
              this.sessionService.goTo('/client/check/successDemo');
            });
          }
        }

      } else {
        this.utilService.showNotification(
          this.translate.instant('error_payment'),
          'danger'
        );
      }
    } else {
      this.utilService.showNotification(
        this.translate.instant('error.terms'),
        'danger'
      );
    }
  }
}
