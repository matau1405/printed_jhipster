import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrintedJhipsterTestModule } from '../../../test.module';
import { CommandeDetailComponent } from 'app/entities/commande/commande-detail.component';
import { Commande } from 'app/shared/model/commande.model';

describe('Component Tests', () => {
  describe('Commande Management Detail Component', () => {
    let comp: CommandeDetailComponent;
    let fixture: ComponentFixture<CommandeDetailComponent>;
    const route = ({ data: of({ commande: new Commande('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrintedJhipsterTestModule],
        declarations: [CommandeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CommandeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommandeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commande).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
