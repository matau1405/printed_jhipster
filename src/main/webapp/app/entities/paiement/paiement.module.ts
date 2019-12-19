import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrintedJhipsterSharedModule } from 'app/shared/shared.module';
import { PaiementComponent } from './paiement.component';
import { PaiementDetailComponent } from './paiement-detail.component';
import { PaiementUpdateComponent } from './paiement-update.component';
import { PaiementDeletePopupComponent, PaiementDeleteDialogComponent } from './paiement-delete-dialog.component';
import { paiementRoute, paiementPopupRoute } from './paiement.route';

const ENTITY_STATES = [...paiementRoute, ...paiementPopupRoute];

@NgModule({
  imports: [PrintedJhipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PaiementComponent,
    PaiementDetailComponent,
    PaiementUpdateComponent,
    PaiementDeleteDialogComponent,
    PaiementDeletePopupComponent
  ],
  entryComponents: [PaiementDeleteDialogComponent]
})
export class PrintedJhipsterPaiementModule {}
