import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrintedJhipsterTestModule } from '../../../test.module';
import { AuthentificationDeleteDialogComponent } from 'app/entities/authentification/authentification-delete-dialog.component';
import { AuthentificationService } from 'app/entities/authentification/authentification.service';

describe('Component Tests', () => {
  describe('Authentification Management Delete Component', () => {
    let comp: AuthentificationDeleteDialogComponent;
    let fixture: ComponentFixture<AuthentificationDeleteDialogComponent>;
    let service: AuthentificationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrintedJhipsterTestModule],
        declarations: [AuthentificationDeleteDialogComponent]
      })
        .overrideTemplate(AuthentificationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuthentificationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuthentificationService);
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
