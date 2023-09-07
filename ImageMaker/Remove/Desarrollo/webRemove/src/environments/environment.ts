// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

// lOCAL

export const environment = {
  production: false,
  url: 'localhost:4600/remove/api/rest',
  withCredentials: false,
  url_stripe: 'http://localhost:4900',
  ssl: false,
  public_key: 'pk_test_51JUGRzE5sKB1kzXkysidvcooe6RHfCJYDZfgKS4F0CTjHW5K3mleNMia2hXHyAf9HJPAom83YC48DAlFLmCqZKWV00GKhJjgTv',

  bucketParams: {
    accessKeyId: 'AKIATV2KEPWAGZ7T2GW6',
    secretAccessKey: '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',
    region: 'us-east-1'
  },
  bucketFileName: 'removefilearchive-prd',
  captcha: {
    sitekey: '6LdZr9EeAAAAAPejAMLGP6oL6IklDScsmcFnb03b',
  },
  captcha2: {
    sitekey: '6LdOQNIeAAAAAP_1wCdNWkLBXUM4_0-OPtcFQx6V',
  },
  version: 20220428
};

// QA

// export const environment = {
//   production: false,
//   url: 'backendqa.removegroup.com/remove/api/rest',
//   withCredentials: true,
//   url_stripe: 'https://qa.removegroup.com',
//   ssl: true,
//   public_key: 'pk_test_51JUGRzE5sKB1kzXkysidvcooe6RHfCJYDZfgKS4F0CTjHW5K3mleNMia2hXHyAf9HJPAom83YC48DAlFLmCqZKWV00GKhJjgTv',

//   bucketParams: {
//     accessKeyId: 'AKIATV2KEPWAGZ7T2GW6',
//     secretAccessKey: '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',
//     region: 'us-east-1'
//   },
//   bucketFileName: 'removefilearchive-prd',
//   captcha: {
//     sitekey: '6LdZr9EeAAAAAPejAMLGP6oL6IklDScsmcFnb03b',
//   },
//   version: 20220428
// };

// PROD

// export const environment = {
//   production: true,
//   url: 'backend.removegroup.com/remove/api/rest',
//   withCredentials: true,
//   url_stripe: 'https://removegroup.com',
//   ssl: true,
//   public_key: 'pk_live_51Jk5CTCoDErZfP4Ufb7tFbe7bSujm8cdWChIlnDVtCG1EvH5yHZbK6kE0RDZQJNqQklkUcB2IrzrX8S2Ypi8CF4J00caln2FxU',

//   bucketParams: {
//     accessKeyId: 'AKIATV2KEPWAGZ7T2GW6',
//     secretAccessKey: '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',
//     region: 'us-east-1'
//   },
//   bucketFileName: 'removefilearchive-prd',
//   captcha: {
//     sitekey: '6LdZr9EeAAAAAPejAMLGP6oL6IklDScsmcFnb03b',
//   },
//   version: 20220428
// };

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
