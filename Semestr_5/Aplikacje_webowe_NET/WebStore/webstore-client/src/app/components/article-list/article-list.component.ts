import { Component, inject, OnInit, signal, HostListener } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ShopService } from '../../services/shop.service';
import { ArticleItemComponent } from '../article-item/article-item.component';
import { AddArticleComponent } from '../add-article/add-article.component';
import { Article } from '../../models/article.model';

@Component({
  selector: 'app-article-list',
  imports: [ArticleItemComponent, AddArticleComponent],
  templateUrl: './article-list.component.html',
  styleUrl: './article-list.component.scss'
})
export class ArticleListComponent implements OnInit {
  shopService = inject(ShopService);
  route = inject(ActivatedRoute);
  
  isModalOpen = signal(false);
  editingArticle = signal<Article | null>(null);

  articles = this.shopService.articles;

  private currentCategoryId: number | undefined = undefined;
  private currentSkip = 0;
  private readonly take = 12;
  private isLoading = false;

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
        const catParam = params.get('id');
        const newCatId = catParam ? Number(catParam) : undefined;

        this.currentCategoryId = newCatId;
        this.currentSkip = 0;
        
        this.shopService.resetArticles(); 
        this.loadMore();
    });
  }

  @HostListener('window:scroll', [])
  onScroll() {
    if (this.isLoading) return;

    const threshold = 10;
    const position = window.innerHeight + Math.ceil(window.scrollY);
    const height = document.documentElement.scrollHeight;

    if (position >= height - threshold) {
      this.loadMore();
    }
  }

  loadMore() {
    this.isLoading = true;
    
    setTimeout(() => {
        this.shopService.loadArticles(this.currentCategoryId, this.currentSkip, this.take);
        this.currentSkip += this.take;
        this.isLoading = false;
    }, 200);
  }

  onDeleteArticle(id: number) {
    this.shopService.removeArticle(id);
  }

  onEditArticle(article: Article) {
    this.editingArticle.set(article);
    this.isModalOpen.set(true);
  }

  openAddModal() {
    this.editingArticle.set(null); 
    this.isModalOpen.set(true);
  }

  closeModal() {
    this.isModalOpen.set(false);
    this.editingArticle.set(null);
  }
}