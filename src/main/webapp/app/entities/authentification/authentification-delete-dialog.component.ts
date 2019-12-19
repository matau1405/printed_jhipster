import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAuthentification } from 'app/shared/model/authentification.model';
import { AuthentificationService } from './authentification.service';

@Component({
  selector: 'jhi-authentification-delete-dialog',
  templateUrl: './authentification-delete-dialog.component.html'
})
export class AuthentificationDeleteDialogComponent {
  authentification: IAuthentification;

  constructor(
    protected authentificationService: AuthentificationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.authentificationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'authentificationListModification',
        content: 'Deleted an authentification'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-authentification-delete-popup',
  template: ''
})
export class AuthentificationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ authentification }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AuthentificationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.authentification = authentification;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/authentification', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/authentification', { outlets: { popup: null } }]);
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
