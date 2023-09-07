import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {DynamicFormHelperService} from 'src/app/helpers/dynamic-form-helper.service';
import {TranslateHelperService} from 'src/app/helpers/translate-helper.service';
import {DeindexationService} from 'src/app/services/deindexation.service';
import {UtilService} from "../../../helpers/util.service";
import {ModalService} from "../../../helpers/modal.service";

@Component({
  selector: 'app-view-deindexing',
  templateUrl: './view-deindexing.component.html',
  styleUrls: ['./view-deindexing.component.css']
})
export class ViewDeindexingComponent implements OnInit {
  private SPLITTER_KEYWORDS = '/';

  info: any = {};
  endingButton = false;
  uuidDeindeindex: string;
  uuidClient: string;
  keywords = [];
  conclusionsParsed = [];
  disabled: boolean = true;

  /**
   * visualize deindexation information
   * @param route
   * @param translate
   * @param utilService
   * @param deindexationService
   * @param dfh
   * @param modal
   */
  constructor(private route: ActivatedRoute, private translate: TranslateHelperService, public utilService: UtilService,
              private deindexationService: DeindexationService, public dfh: DynamicFormHelperService, private modal: ModalService) {
    this.uuidDeindeindex = this.route.snapshot.paramMap.get('uuid_deindexation');
    this.uuidClient = this.route.snapshot.paramMap.get('uuid_client');
  }

  ngOnInit(): void {
    this.loadDeindexation();
  }

  /**
   * load the deindexidation info
   * @private
   */
  private loadDeindexation(): void {
    this.deindexationService.get(this.uuidDeindeindex, this.uuidClient, response => {
      this.info = response;
      this.splitKeywords();
      this.checkDisableForm();
      this.dfh.initializeForm(this.info.dynamicForm);
      this.replaceConclusionTokens(this.info.conclusions);
    })
  }

  /**
   * get the tokens in the conclusion and replaced
   * @param conclusions
   */
  replaceConclusionTokens(conclusions: any[]) {
    this.conclusionsParsed = [];
    let mapValues = this.dfh.refreshMapValues(this.info.dynamicForm);

    if (conclusions) {
      conclusions.forEach(conclusion => {
        this.conclusionsParsed.push(this.dfh.replaceTokens(mapValues, conclusion));
      })
    }
  }

  /**
   * get a keywords array and splited at the begging
   */
  splitKeywords() {
    if (this.info?.url_to_deindex_keywords?.length > 0) {
      this.keywords = this.info.url_to_deindex_keywords.split(this.SPLITTER_KEYWORDS);
    }
  }

  /**
   * refresh URL status
   * @param url
   */
  markSentUrl(url: any): void {
    this.modal.openConfirmation({
      message: 'message.deindex.send',
      onConfirm: () => {
        this.deindexationService.refreshUrlStatus(url);
      }
    });

  }

  /**
   * Deprecados
   markAsSentToGoogle(): void {
    this.deindexationService.markAsSentGoogle(this.info.id, 'AQUI_VA_EL_TRACKING_CODE_DE_EXISTIR', () => {
      this.loadDeindexation();
    })
  }
   markAsRespondedGoogle(): void {
    this.deindexationService.markAsRespondedGoogle(this.info.id, () => {
      this.loadDeindexation();
    })
  }
   markAsSentMedia(): void {
    this.deindexationService.markAsSentMedia(this.info.id, () => {
      this.loadDeindexation();
    })
  }
   markAsRespondedMedia(): void {
    this.deindexationService.markAsRespondedMedia(this.info.id, () => {
      this.loadDeindexation();
    })
  }
   */
  /**
   * change status to approved
   */
  markAsApproved(): void {

    this.modal.openConfirmation({
      message: 'message.deindex.aproved',
      onConfirm: () => {
        this.deindexationService.markAsApproved(this.info.id, () => {
          this.loadDeindexation();
        });
      }
    });
  }

  /**
   * change status to  rejected
   */
  markAsRejected(): void {
    this.modal.openConfirmation({
      message: 'message.deindex.reject',
      onConfirm: () => {
        this.deindexationService.markAsRejected(this.info.id, () => {
          this.loadDeindexation();
        });
      }
    });
  }

  /**
   * change status to deleted
   */
  markAsDeleted(): void {
    this.modal.openConfirmation({
      message: 'message.deindex.delete',
      onConfirm: () => {
        this.deindexationService.markAsDeleted(this.info.id, () => {
          this.loadDeindexation();
        });
      }
    });
  }

  /**
   * go back in the navigation history
   */
  back() {
    this.utilService.back();
  }

  /**
   * Disabled form by status: Just editable when PROCESSING, APPROVED
   */
   private checkDisableForm(): void {
    this.disabled = this.info.statusDef != null && !this.info.statusDef.PROCESSING && !this.info.statusDef.APPROVED;
  }
}
