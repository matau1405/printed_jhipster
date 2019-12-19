import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { CommandeService } from 'app/entities/commande/commande.service';
import { ICommande, Commande } from 'app/shared/model/commande.model';

describe('Service Tests', () => {
  describe('Commande Service', () => {
    let injector: TestBed;
    let service: CommandeService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommande;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CommandeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Commande('ID', 'AAAAAAA', 'AAAAAAA', 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find('123')
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Commande', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Commande(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Commande', () => {
        const returnedFromService = Object.assign(
          {
            idCmd: 'BBBBBB',
            dateCmd: 'BBBBBB',
            montantCmd: 1,
            delaiLivraisonCmd: 1,
            etatLivraisonCmd: 'BBBBBB',
            lieuLivraisonCmd: 'BBBBBB',
            modeLivraisonCmd: 'BBBBBB',
            prixTotalCmd: 1,
            modePaiement: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Commande', () => {
        const returnedFromService = Object.assign(
          {
            idCmd: 'BBBBBB',
            dateCmd: 'BBBBBB',
            montantCmd: 1,
            delaiLivraisonCmd: 1,
            etatLivraisonCmd: 'BBBBBB',
            lieuLivraisonCmd: 'BBBBBB',
            modeLivraisonCmd: 'BBBBBB',
            prixTotalCmd: 1,
            modePaiement: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Commande', () => {
        service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
