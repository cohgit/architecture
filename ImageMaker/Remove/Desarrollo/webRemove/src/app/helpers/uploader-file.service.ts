import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import * as AWS from 'aws-sdk/global';
import * as S3 from 'aws-sdk/clients/s3';
import { SessionService } from './session.service';
import {UtilService} from "./util.service";

@Injectable({
  providedIn: 'root'
})
export class UploaderFileService {
  /**
   * allows you to manage the connection between the system and AWS S3 for file management
   * @param session
   */
  constructor(public session: SessionService, private utilService: UtilService) { }

  /**
   * Download File from S3 by key.
   * @param key
   * @param success
   * @param error
   */
  download(key: string, success?: Function, error?: Function) {
    let filename = this.getOriginalName(key);
    const bucket = new S3(environment.bucketParams);
    const params = {
      Bucket: environment.bucketFileName,
      Key: key
    }

    bucket.getObject(params, function(err, data) {
      if (err) {
        console.error('There was an error downloading your file: ', err);
        if (error) error(err);
        return false;
      }
      if (success)
        success(data);
      else {
        const dataUrl = `data:${data.ContentType};base64,${data.Body.toString('base64')}`;
        const a: HTMLAnchorElement = document.createElement('a') as HTMLAnchorElement;

        a.href = dataUrl;
        a.download = filename;
        document.body.appendChild(a);
        a.click();

        document.body.removeChild(a);
      }
    })
  }

  /**
   * Get the information of a file for the preview of a document, for example
   * @param key
   * @param success
   * @param error
   */
  getData(key: string, success?: Function, error?: Function) {
    const bucket = new S3(environment.bucketParams);
    const params = {
      Bucket: environment.bucketFileName,
      Key: key
    }

    bucket.getObject(params, function(err, data) {
      if (err) {
        console.error('There was an error downloading your file: ', err);
        if (error) error(err);
        return false;
      }
      if (success)
        success(`data:${data.ContentType};base64,${data.Body.toString('base64')}`);
    })
  }

  /**
   * Upload file to S3
   * @param file
   * @param success
   * @param error
   */
  upload(file: any, success?: Function, error?: Function):void {
    const bucket = new S3(environment.bucketParams);
    const params = this.buildBucketParams(file);
    // console.log('bucket', bucket, 'params', params);
    bucket.upload(params, function (err, data) {
      if (err) {
        console.error('There was an error uploading your file: ', err);
        if (error) error(err);
        return false;
      }

      if (success) success(data.Key);

      return true;
    });
  }

  //For upload progress (Pending test)
  uploadProgress(file: any, success?: Function, error?: Function) {
    const bucket = new S3(environment.bucketParams);
    const params = this.buildBucketParams(file);


    bucket.upload(params).on('httpUploadProgress', function (evt) {
    }).send(function (err, data) {
      if (err) {
        console.error('There was an error uploading your file: ', err);
        if (error) error(err);
        return false;
      }

      if (success) success(data.Key);

      return true;
    });
  }

  /**
   * Upload file from S3 by key
   * @param key
   * @param success
   * @param error
   */
  delete(key: string, success?: Function, error?: Function) {
    let filename = this.getOriginalName(key);
    const bucket = new S3(environment.bucketParams);
    const params = {
      Bucket: environment.bucketFileName,
      Key: key
    }

    bucket.deleteObject(params, function(err, data) {
      if (err) {
        console.error('There was an error deleting your file: ', err);
        if (error) error(err);
        return false;
      }
      if (success)
        success(data);
    })
  }

  /**
   *
   * @param file
   * @private
   */
  private buildBucketParams(file: any): any {
    return {
        Bucket: environment.bucketFileName,
        Key: this.getUserFolder() + this.buildFileName(file.name),
        Body: file,
        ACL: 'public-read',
        ContentType: file.type
    }
  }

  /**
   * Check the path of the folder where the file is located
   * @private
   */
  private getModuleFolder(): string {
    const module = '/module/';
    if (window.location.href.includes(module)) {
      return window.location.href
        .split(module)[1]
        .split('/')[0]
        .split('?')[0];
    }

    return "external";
  }

  /**
   * Check the path of the folder where the folder of the user is located
   * @private
   */
  private getUserFolder(): string {
    const userFolder = this.session.getUser() ? this.session.getUser().uuid : "anonymous";

    return this.getModuleFolder() + "/" + userFolder + "/";
  }

  /**
   * build filename with current date, clean the name from special characters and spaces
   * @param originalName
   * @private
   */
  private buildFileName(originalName: string): string {
    return Date.now().valueOf() + '-' + this.utilService.cleanString(originalName);
  }

  /**
   * get the name of the original file
   * @param name
   */
  public getOriginalName(name: string): string {
    const splittedKey = name.split("/");

    return splittedKey[splittedKey.length - 1].substring(splittedKey[splittedKey.length - 1].indexOf("-")+1);
  }
}
