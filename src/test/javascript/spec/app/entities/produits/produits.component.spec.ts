import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PrintedJhipsterTestModule } from '../../../test.module';
import { ProduitsComponent } from 'app/entities/produits/produits.component';
import { ProduitsService } from 'app/entities/produits/produits.service';
import { Produits } from 'app/shared/model/produits.model';

describe('Component Tests', () => {
  describe('Produits Management Component', () => {
    let comp: ProduitsComponent;
    let fixture: ComponentFixture<ProduitsComponent>;
    let service: ProduitsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrintedJhipsterTestModule],
        declarations: [ProduitsComponent],
        providers: []
      })
        .overrideTemplate(ProduitsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProduitsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProduitsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Produits('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.produits[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
