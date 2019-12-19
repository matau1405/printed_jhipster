import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrintedJhipsterTestModule } from '../../../test.module';
import { ProduitsDetailComponent } from 'app/entities/produits/produits-detail.component';
import { Produits } from 'app/shared/model/produits.model';

describe('Component Tests', () => {
  describe('Produits Management Detail Component', () => {
    let comp: ProduitsDetailComponent;
    let fixture: ComponentFixture<ProduitsDetailComponent>;
    const route = ({ data: of({ produits: new Produits('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrintedJhipsterTestModule],
        declarations: [ProduitsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProduitsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProduitsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.produits).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
