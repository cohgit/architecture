import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {loadStripe} from '@stripe/stripe-js';

@Injectable({
  providedIn: 'root'
})
export class PaymentStripeService {
  // stripe: any;
  // stripePromise = loadStripe(environment.public_key);
  urlStripe = window.location.origin;

  /**
   * It is used when a new client is created and pays for its license with stripe on the platform.
   */
  constructor() {
    //   this.initialized();
  }

  //async initialized() {
  // this.stripe = await this.stripePromise;
  //}

  /**
   * Generate a recurring paid stripe subscription
   * @param client
   * @param plan
   */
  async checkoutSubscription(client, plan): Promise<any> {
    return await (await loadStripe(environment.public_key)).redirectToCheckout({
      mode: 'subscription',
      customerEmail: client,
      lineItems: [{price: plan, quantity: 1}],
      successUrl: this.urlStripe + '/#/client/check/success/{CHECKOUT_SESSION_ID}',
      cancelUrl: this.urlStripe + '/#/client/check/fail',
    });
  }

  /**
   * Generate a one-time payment to stripe
   * @param client
   * @param plan
   */
  async checkoutOneTime(client, plan): Promise<any> {
    return await (await loadStripe(environment.public_key)).redirectToCheckout({
      mode: 'payment',
      customerEmail: client,
      lineItems: [{price: plan, quantity: 1}],
      successUrl: this.urlStripe + '/#/client/check/success/{CHECKOUT_SESSION_ID}',
      cancelUrl: this.urlStripe + '/#/client/check/fail',
    });
  }
}
