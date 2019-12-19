import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrintedJhipsterTestModule } from '../../../test.module';
import { PanierDeleteDialogComponent } from 'app/entities/panier/panier-delete-dialog.component';
import { PanierService } from 'app/entities/panier/panier.service';

describe('Component Tests', () => {
  describe('Panier Management Delete Component', () => {
    let comp: PanierDeleteDialogComponent;
    let fixture: ComponentFixture<PanierDeleteDialogComponent>;
    let service: PanierService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrintedJhipsterTestModule],
        declarations: [PanierDeleteDialogComponent]
      })
        .overrideTemplate(PanierDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PanierDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PanierService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('123');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('123');
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
