import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrintedJhipsterSharedModule } from 'app/shared/shared.module';
import { ProduitsComponent } from './produits.component';
import { ProduitsDetailComponent } from './produits-detail.component';
import { ProduitsUpdateComponent } from './produits-update.component';
import { ProduitsDeletePopupComponent, ProduitsDeleteDialogComponent } from './produits-delete-dialog.component';
import { produitsRoute, produitsPopupRoute } from './produits.route';

const ENTITY_STATES = [...produitsRoute, ...produitsPopupRoute];

@NgModule({
  imports: [PrintedJhipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProduitsComponent,
    ProduitsDetailComponent,
    ProduitsUpdateComponent,
    ProduitsDeleteDialogComponent,
    ProduitsDeletePopupComponent
  ],
  entryComponents: [ProduitsDeleteDialogComponent]
})
export class PrintedJhipsterProduitsModule {}
