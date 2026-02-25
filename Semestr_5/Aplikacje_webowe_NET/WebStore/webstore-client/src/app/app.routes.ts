import { Routes } from '@angular/router';
import { ArticleListComponent } from './components/article-list/article-list.component';
import { ShopLayoutComponent } from './components/shop-layout/shop-layout.component';
import { ArticleDetailsComponent } from './components/article-details/article-details.component';

export const routes: Routes = [
  { path: '', redirectTo: 'shop', pathMatch: 'full' },
  { 
    path: 'shop', 
    component: ShopLayoutComponent,
    children: [
      { path: '', component: ArticleListComponent },
      { path: 'category/:id', component: ArticleListComponent },
      { path: 'article/:id', component: ArticleDetailsComponent }
    ]
  },
  { path: '**', redirectTo: 'shop' }
];