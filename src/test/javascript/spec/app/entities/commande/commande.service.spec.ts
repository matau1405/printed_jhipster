import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CommandeService } from 'app/entities/commande/commande.service';
import { ICommande, Commande } from 'app/shared/model/commande.model';
import { ModeDeLivraison } from 'app/shared/model/enumerations/mode-de-livraison.model';
import { StatusCommande } from 'app/shared/model/enumerations/status-commande.model';

describe('Service Tests', () => {
  describe('Commande Service', () => {
    let injector: TestBed;
    let service: CommandeService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommande;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CommandeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Commande(
        'ID',
        'AAAAAAA',
        currentDate,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        ModeDeLivraison.LivraisonExpress,
        StatusCommande.EnCoursDeValidation
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateCmd: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
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
            id: 'ID',
            dateCmd: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateCmd: currentDate
          },
          returnedFromService
        );
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
            dateCmd: currentDate.format(DATE_TIME_FORMAT),
            montantCmd: 1,
            delaiLivraisonCmd: 1,
            etatLivraisonCmd: 'BBBBBB',
            lieuLivraisonCmd: 'BBBBBB',
            modeLivraisonCmd: 'BBBBBB',
            status: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCmd: currentDate
          },
          returnedFromService
        );
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
            dateCmd: currentDate.format(DATE_TIME_FORMAT),
            montantCmd: 1,
            delaiLivraisonCmd: 1,
            etatLivraisonCmd: 'BBBBBB',
            lieuLivraisonCmd: 'BBBBBB',
            modeLivraisonCmd: 'BBBBBB',
            status: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateCmd: currentDate
          },
          returnedFromService
        );
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
