import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrintedJhipsterSharedModule } from 'app/shared/shared.module';
import { PanierComponent } from './panier.component';
import { PanierDetailComponent } from './panier-detail.component';
import { PanierUpdateComponent } from './panier-update.component';
import { PanierDeletePopupComponent, PanierDeleteDialogComponent } from './panier-delete-dialog.component';
import { panierRoute, panierPopupRoute } from './panier.route';

const ENTITY_STATES = [...panierRoute, ...panierPopupRoute];

@NgModule({
  imports: [PrintedJhipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PanierComponent, PanierDetailComponent, PanierUpdateComponent, PanierDeleteDialogComponent, PanierDeletePopupComponent],
  entryComponents: [PanierDeleteDialogComponent]
})
export class PrintedJhipsterPanierModule {}
