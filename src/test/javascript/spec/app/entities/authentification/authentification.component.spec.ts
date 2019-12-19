import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PrintedJhipsterTestModule } from '../../../test.module';
import { AuthentificationComponent } from 'app/entities/authentification/authentification.component';
import { AuthentificationService } from 'app/entities/authentification/authentification.service';
import { Authentification } from 'app/shared/model/authentification.model';

describe('Component Tests', () => {
  describe('Authentification Management Component', () => {
    let comp: AuthentificationComponent;
    let fixture: ComponentFixture<AuthentificationComponent>;
    let service: AuthentificationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrintedJhipsterTestModule],
        declarations: [AuthentificationComponent],
        providers: []
      })
        .overrideTemplate(AuthentificationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuthentificationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuthentificationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Authentification('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.authentifications[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
