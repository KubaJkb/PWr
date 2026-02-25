import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Article, Category } from '../models/article.model';

@Injectable({
  providedIn: 'root'
})
export class ShopService {
  private http = inject(HttpClient);
  
  public readonly apiUrl = 'http://localhost:5168/api/articles'; 
  private storageKey = 'webstore_articles_cache';

  categories: Category[] = [
    { id: 1, name: 'Elektronika' },
    { id: 2, name: 'Spożywcze' },
    { id: 3, name: 'Odzież' },
    { id: 4, name: 'Chemia' },
    { id: 5, name: 'Inne' }
  ];

  articles = signal<Article[]>([]);

  constructor() {
    this.loadFromStorage();
  }

  loadArticles(categoryId?: number, skip: number = 0, take: number = 12) {
    let url = `${this.apiUrl}?skip=${skip}&take=${take}`;
    if (categoryId) {
      url += `&categoryId=${categoryId}`;
    }

    this.http.get<Article[]>(url).subscribe({
      next: (data) => {
        if (skip === 0) {
          this.articles.set(data);
        } else {
          this.articles.update(currentArticles => [...currentArticles, ...data]);
        }
        
        if (this.articles().length > 0) {
            this.saveToStorage(this.articles());
        }
      },
      error: (err) => console.error('Błąd pobierania:', err)
    });
  }

  resetArticles() {
    this.articles.set([]);
  }

  getArticleById(id: number) {
    return this.http.get<Article>(`${this.apiUrl}/${id}`);
  }

  addArticle(name: string, categoryId: number, price: number, date: string) {
    const cat = this.categories.find(c => c.id === categoryId);
    
    const newArticle: Partial<Article> = {
      name: name,
      categoryId: categoryId,
      price: price,
      expirationDate: date,
      imageName: null
    };

    this.http.post<Article>(this.apiUrl, newArticle).subscribe({
      next: (created) => {
        created.category = cat;
        this.articles.update(list => [...list, created]);
        this.saveToStorage(this.articles());
      },
      error: (err) => console.error('Błąd dodawania:', err)
    });
  }

  updateArticle(updatedArticle: Article) {
    this.http.put<void>(`${this.apiUrl}/${updatedArticle.id}`, updatedArticle).subscribe({
      next: () => {
        this.articles.update(list => 
          list.map(a => a.id === updatedArticle.id ? updatedArticle : a)
        );
        this.saveToStorage(this.articles());
      },
      error: (err) => console.error('Błąd edycji:', err)
    });
  }

  removeArticle(id: number) {
    this.http.delete<void>(`${this.apiUrl}/${id}`).subscribe({
      next: () => {
        this.articles.update(list => list.filter(a => a.id !== id));
        this.saveToStorage(this.articles());
      },
      error: (err) => console.error('Błąd usuwania:', err)
    });
  }

  private saveToStorage(data: Article[]) {
    localStorage.setItem(this.storageKey, JSON.stringify(data));
  }

  private loadFromStorage() {
    const data = localStorage.getItem(this.storageKey);
    if (data) {
      this.articles.set(JSON.parse(data));
    }
  }
}