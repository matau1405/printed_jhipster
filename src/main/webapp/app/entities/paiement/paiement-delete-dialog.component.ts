import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaiement } from 'app/shared/model/paiement.model';
import { PaiementService } from './paiement.service';

@Component({
  selector: 'jhi-paiement-delete-dialog',
  templateUrl: './paiement-delete-dialog.component.html'
})
export class PaiementDeleteDialogComponent {
  paiement: IPaiement;

  constructor(protected paiementService: PaiementService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.paiementService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'paiementListModification',
        content: 'Deleted an paiement'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-paiement-delete-popup',
  template: ''
})
export class PaiementDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paiement }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PaiementDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.paiement = paiement;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/paiement', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/paiement', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
