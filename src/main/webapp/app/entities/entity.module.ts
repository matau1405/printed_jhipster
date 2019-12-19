import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'produits',
        loadChildren: () => import('./produits/produits.module').then(m => m.PrintedJhipsterProduitsModule)
      },
      {
        path: 'panier',
        loadChildren: () => import('./panier/panier.module').then(m => m.PrintedJhipsterPanierModule)
      },
      {
        path: 'commande',
        loadChildren: () => import('./commande/commande.module').then(m => m.PrintedJhipsterCommandeModule)
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.PrintedJhipsterClientModule)
      },
      {
        path: 'paiement',
        loadChildren: () => import('./paiement/paiement.module').then(m => m.PrintedJhipsterPaiementModule)
      },
      {
        path: 'authentification',
        loadChildren: () => import('./authentification/authentification.module').then(m => m.PrintedJhipsterAuthentificationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PrintedJhipsterEntityModule {}
