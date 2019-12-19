import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ClientService } from 'app/entities/client/client.service';
import { IClient, Client } from 'app/shared/model/client.model';

describe('Service Tests', () => {
  describe('Client Service', () => {
    let injector: TestBed;
    let service: ClientService;
    let httpMock: HttpTestingController;
    let elemDefault: IClient;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ClientService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Client('ID', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateNaissanceClient: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Client', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            dateNaissanceClient: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateNaissanceClient: currentDate
          },
          returnedFromService
        );
        service
          .create(new Client(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Client', () => {
        const returnedFromService = Object.assign(
          {
            idClient: 'BBBBBB',
            nomClient: 'BBBBBB',
            prenomClient: 'BBBBBB',
            dateNaissanceClient: currentDate.format(DATE_TIME_FORMAT),
            adresseClient: 'BBBBBB',
            villeClient: 'BBBBBB',
            paysClient: 'BBBBBB',
            emailClient: 'BBBBBB',
            listCommande: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissanceClient: currentDate
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

      it('should return a list of Client', () => {
        const returnedFromService = Object.assign(
          {
            idClient: 'BBBBBB',
            nomClient: 'BBBBBB',
            prenomClient: 'BBBBBB',
            dateNaissanceClient: currentDate.format(DATE_TIME_FORMAT),
            adresseClient: 'BBBBBB',
            villeClient: 'BBBBBB',
            paysClient: 'BBBBBB',
            emailClient: 'BBBBBB',
            listCommande: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateNaissanceClient: currentDate
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

      it('should delete a Client', () => {
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
