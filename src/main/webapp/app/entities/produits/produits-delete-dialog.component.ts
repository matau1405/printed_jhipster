import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProduits } from 'app/shared/model/produits.model';
import { ProduitsService } from './produits.service';

@Component({
  selector: 'jhi-produits-delete-dialog',
  templateUrl: './produits-delete-dialog.component.html'
})
export class ProduitsDeleteDialogComponent {
  produits: IProduits;

  constructor(protected produitsService: ProduitsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.produitsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'produitsListModification',
        content: 'Deleted an produits'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-produits-delete-popup',
  template: ''
})
export class ProduitsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ produits }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProduitsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.produits = produits;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/produits', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/produits', { outlets: { popup: null } }]);
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
