import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { UtilService } from 'src/app/helpers/util.service';

@Component({
  selector: 'app-file-input',
  templateUrl: './file-input.component.html',
  styleUrls: ['./file-input.component.css']
})
/**
 * formats posible values
 * file_extension	Specify the file extension(s) (e.g: .gif, .jpg, .png, .doc) the user can pick from
    audio/*	The user can pick all sound files
    video/*	The user can pick all video files
    image/*	The user can pick all image files
    media_type	A valid media type, with no parameters. Look at IANA Media Types for a complete list of standard media types
 */
export class FileInputComponent implements OnInit {
  VALID_FORMATS = {
    AUDIO: 'audio/*',
    VIDEO: 'video/*',
    IMAGE: 'image/*',
    PDF: '.pdf'
  };

  @Input() fileTitle: string;
  @Input() model: any;
  @Input() formats: string;
  @Input() disabled = false;
  @Input() postview: string;
  @Input() preview = false;
  @Input() rescale = false;
  @Input() previewWidth = 300;
  @Input() previewHeight = 200;

  @Output() upload = new EventEmitter<any>();
  @Output() download = new EventEmitter<any>();

  accept = "";
  errors: string;
  base64Preview: any;

  constructor(private utilService: UtilService) { }

  ngOnInit(): void {
    if (this.formats) {
      this.accept = this.VALID_FORMATS[this.formats] ? this.VALID_FORMATS[this.formats] : this.formats;
    } else {
      this.accept = this.VALID_FORMATS.PDF;
    }
  }

  /**
   * download a file
   */
  _download() {
    this.download.emit(this.model);
  }

  /**
   * upload a file
   * @param $event
   */
  _upload($event) {
    let file = $event.target.files[0];
    this._validateUpload(file);

    if (!this.errors) {
      this._validateRescale(file, _file => {
        this.upload.emit({data: this.model, file: _file});
      });
    }
  }

  /**
   *
   * @param file
   */
  _validateUpload(file: any) {
    this.errors = null;

    if (this.accept.endsWith("/*")) {
      if (!file.type.startsWith(this.accept.substring(0, this.accept.length-1))) {
        this.errors = "Archivo Inválido";
      }
    } else if (this.accept.startsWith(".")){
      if (!file.type.endsWith(this.accept.substring(1, this.accept.length))) {
        this.errors = "Archivo Inválido";
      }
    }
  }

  /**
   *
   * @param file
   * @param isValid
   */
  _validateRescale(file: any, isValid: Function) {
    let _file = file;
    if (this.rescale) {
      const w = this.previewWidth ? this.previewWidth : 300;
      const h = this.previewHeight ? this.previewHeight : 200;

      this._resizeImage(file, w, h).then(result => {
        let b: any = result;
        //A Blob() is almost a File() - it's just missing the two properties below which we will add
        b.lastModifiedDate = file.lastModifiedDate;
        b.lastModified = file.lastModified;
        b.name = file.name;

        _file = <File> b;

        this._validatePreview(_file);
        isValid(_file);
      })
    } else {
      this._validatePreview(_file);
      isValid(_file);
    }
  }

  /**
   *
   * @param file
   */
  _validatePreview(file: any) {
    if (this.preview) {
      this.utilService.getBase64(file).then(result => {
        this.base64Preview = result;
      });
    }
  }

  /**
   *
   */
  _reset() {
    this._remove();
    this.errors = null;
  }

  /**
   *
   */
  _remove() {
    this.model.id_content = null;
  }

  /**
   *
   * @param file
   * @param maxWidth
   * @param maxHeight
   */
  _resizeImage(file:File, maxWidth:number, maxHeight:number):Promise<Blob> {
    return new Promise((resolve, reject) => {
        let image = new Image();
        image.src = URL.createObjectURL(file);
        image.onload = () => {
            let width = image.width;
            let height = image.height;

            if (width <= maxWidth && height <= maxHeight) {
              this.previewWidth = width;
              this.previewHeight = height;

              resolve(file);
            } else {
              let newWidth;
              let newHeight;

              if (width > height) {
                  newHeight = height * (maxWidth / width);
                  newWidth = maxWidth;
              } else {
                  newWidth = width * (maxHeight / height);
                  newHeight = maxHeight;
              }
              this.previewWidth = newWidth;
              this.previewHeight = newHeight;

              let canvas = document.createElement('canvas');
              canvas.width = newWidth;
              canvas.height = newHeight;

              let context = canvas.getContext('2d');

              context.drawImage(image, 0, 0, newWidth, newHeight);

              canvas.toBlob(resolve, file.type);
            }
        };
        image.onerror = reject;
    });
  }
}
