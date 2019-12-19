import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { ProduitsService } from 'app/entities/produits/produits.service';
import { IProduits, Produits } from 'app/shared/model/produits.model';

describe('Service Tests', () => {
  describe('Produits Service', () => {
    let injector: TestBed;
    let service: ProduitsService;
    let httpMock: HttpTestingController;
    let elemDefault: IProduits;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ProduitsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Produits('ID', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false, 0, 'AAAAAAA', false, 'AAAAAAA', 'AAAAAAA');
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

      it('should create a Produits', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Produits(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Produits', () => {
        const returnedFromService = Object.assign(
          {
            idProd: 'BBBBBB',
            nomProd: 'BBBBBB',
            descriptionProd: 'BBBBBB',
            prixProd: 'BBBBBB',
            dispo: true,
            stock: 1,
            marque: 'BBBBBB',
            personnalisable: true,
            imageProd: 'BBBBBB',
            imagePersonnalisation: 'BBBBBB'
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

      it('should return a list of Produits', () => {
        const returnedFromService = Object.assign(
          {
            idProd: 'BBBBBB',
            nomProd: 'BBBBBB',
            descriptionProd: 'BBBBBB',
            prixProd: 'BBBBBB',
            dispo: true,
            stock: 1,
            marque: 'BBBBBB',
            personnalisable: true,
            imageProd: 'BBBBBB',
            imagePersonnalisation: 'BBBBBB'
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

      it('should delete a Produits', () => {
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
