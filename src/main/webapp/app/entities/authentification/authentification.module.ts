import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrintedJhipsterSharedModule } from 'app/shared/shared.module';
import { AuthentificationComponent } from './authentification.component';
import { AuthentificationDetailComponent } from './authentification-detail.component';
import { AuthentificationUpdateComponent } from './authentification-update.component';
import { AuthentificationDeletePopupComponent, AuthentificationDeleteDialogComponent } from './authentification-delete-dialog.component';
import { authentificationRoute, authentificationPopupRoute } from './authentification.route';

const ENTITY_STATES = [...authentificationRoute, ...authentificationPopupRoute];

@NgModule({
  imports: [PrintedJhipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AuthentificationComponent,
    AuthentificationDetailComponent,
    AuthentificationUpdateComponent,
    AuthentificationDeleteDialogComponent,
    AuthentificationDeletePopupComponent
  ],
  entryComponents: [AuthentificationDeleteDialogComponent]
})
export class PrintedJhipsterAuthentificationModule {}
