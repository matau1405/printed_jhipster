import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PrintedJhipsterTestModule } from '../../../test.module';
import { ProduitsUpdateComponent } from 'app/entities/produits/produits-update.component';
import { ProduitsService } from 'app/entities/produits/produits.service';
import { Produits } from 'app/shared/model/produits.model';

describe('Component Tests', () => {
  describe('Produits Management Update Component', () => {
    let comp: ProduitsUpdateComponent;
    let fixture: ComponentFixture<ProduitsUpdateComponent>;
    let service: ProduitsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrintedJhipsterTestModule],
        declarations: [ProduitsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProduitsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProduitsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProduitsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Produits('123');
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
        const entity = new Produits();
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
