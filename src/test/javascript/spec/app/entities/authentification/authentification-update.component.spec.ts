import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PrintedJhipsterTestModule } from '../../../test.module';
import { AuthentificationUpdateComponent } from 'app/entities/authentification/authentification-update.component';
import { AuthentificationService } from 'app/entities/authentification/authentification.service';
import { Authentification } from 'app/shared/model/authentification.model';

describe('Component Tests', () => {
  describe('Authentification Management Update Component', () => {
    let comp: AuthentificationUpdateComponent;
    let fixture: ComponentFixture<AuthentificationUpdateComponent>;
    let service: AuthentificationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrintedJhipsterTestModule],
        declarations: [AuthentificationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AuthentificationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuthentificationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuthentificationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Authentification('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Authentification();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
