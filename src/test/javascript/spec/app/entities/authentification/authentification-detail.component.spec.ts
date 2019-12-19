import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrintedJhipsterTestModule } from '../../../test.module';
import { AuthentificationDetailComponent } from 'app/entities/authentification/authentification-detail.component';
import { Authentification } from 'app/shared/model/authentification.model';

describe('Component Tests', () => {
  describe('Authentification Management Detail Component', () => {
    let comp: AuthentificationDetailComponent;
    let fixture: ComponentFixture<AuthentificationDetailComponent>;
    const route = ({ data: of({ authentification: new Authentification('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrintedJhipsterTestModule],
        declarations: [AuthentificationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AuthentificationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuthentificationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.authentification).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
