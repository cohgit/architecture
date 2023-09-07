import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { UploaderFileService } from '../../helpers/uploader-file.service';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css'],
})

export class FileUploadComponent implements OnInit {
  selectedFiles: FileList;
  @Input() fileType: string = "";
  @Input() model: any;  // TODO: Fix this for standalone behavior
  @Output() modelChange = new EventEmitter<number>();

  @Output() onUpload = new EventEmitter<any>();
  @Output() onDownload = new EventEmitter<any>();
  @Output() onDelete = new EventEmitter<any>();

  constructor(private uploadService: UploaderFileService) {}

  ngOnInit() {}

  /**
   * Upload a file
   */
  upload() {
    const file = this.selectedFiles.item(0);

    this.uploadService.upload(file, keyFile => {
      this.model = keyFile;

      if (this.onUpload) {
        this.onUpload.emit(keyFile);
      }
    });
  }

  /**
   * download a file
   */
  download() {
    this.uploadService.download(this.model, data => {
      if (this.onDownload) {
        this.onDownload.emit(data);
      }
    });
  }

  /**
   * delete a file
   */
  delete() {
    this.uploadService.delete(this.model, () => {
      if (this.onDelete) {
        this.onDelete.emit();
      }
    });
  }

  /**
   * select a file
   * @param event
   */
  selectFile(event) {
    this.selectedFiles = event.target.files;
  }
}
