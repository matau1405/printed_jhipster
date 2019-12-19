import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrintedJhipsterTestModule } from '../../../test.module';
import { ProduitsDeleteDialogComponent } from 'app/entities/produits/produits-delete-dialog.component';
import { ProduitsService } from 'app/entities/produits/produits.service';

describe('Component Tests', () => {
  describe('Produits Management Delete Component', () => {
    let comp: ProduitsDeleteDialogComponent;
    let fixture: ComponentFixture<ProduitsDeleteDialogComponent>;
    let service: ProduitsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrintedJhipsterTestModule],
        declarations: [ProduitsDeleteDialogComponent]
      })
        .overrideTemplate(ProduitsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProduitsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProduitsService);
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
