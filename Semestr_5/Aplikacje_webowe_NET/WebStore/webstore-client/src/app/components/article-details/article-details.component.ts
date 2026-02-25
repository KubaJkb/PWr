import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { ShopService } from '../../services/shop.service';
import { Article } from '../../models/article.model';

@Component({
  selector: 'app-article-details',
  imports: [CommonModule],
  templateUrl: './article-details.component.html',
  styleUrl: './article-details.component.scss'
})
export class ArticleDetailsComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private shopService = inject(ShopService);

  article = signal<Article | undefined>(undefined);

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      if (id) {
        this.loadArticle(id);
      }
    });
  }

  loadArticle(id: number) {
    const cached = this.shopService.articles().find(a => a.id === id);
    if (cached) {
      this.article.set(cached);
    } else {
      this.shopService.getArticleById(id).subscribe({
        next: (data) => this.article.set(data),
        error: () => this.router.navigate(['/shop'])
      });
    }
  }

  goBack() {
    this.router.navigate(['/shop']);
  }
}