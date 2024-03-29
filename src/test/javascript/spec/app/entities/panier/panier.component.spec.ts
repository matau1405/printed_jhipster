import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PrintedJhipsterTestModule } from '../../../test.module';
import { PanierComponent } from 'app/entities/panier/panier.component';
import { PanierService } from 'app/entities/panier/panier.service';
import { Panier } from 'app/shared/model/panier.model';

describe('Component Tests', () => {
  describe('Panier Management Component', () => {
    let comp: PanierComponent;
    let fixture: ComponentFixture<PanierComponent>;
    let service: PanierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrintedJhipsterTestModule],
        declarations: [PanierComponent],
        providers: []
      })
        .overrideTemplate(PanierComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PanierComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PanierService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Panier('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paniers[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
