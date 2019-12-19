import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPanier } from 'app/shared/model/panier.model';
import { PanierService } from './panier.service';

@Component({
  selector: 'jhi-panier-delete-dialog',
  templateUrl: './panier-delete-dialog.component.html'
})
export class PanierDeleteDialogComponent {
  panier: IPanier;

  constructor(protected panierService: PanierService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.panierService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'panierListModification',
        content: 'Deleted an panier'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-panier-delete-popup',
  template: ''
})
export class PanierDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ panier }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PanierDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.panier = panier;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/panier', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/panier', { outlets: { popup: null } }]);
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
