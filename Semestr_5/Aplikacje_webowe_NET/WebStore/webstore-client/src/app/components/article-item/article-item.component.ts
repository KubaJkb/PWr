import { Component, input, output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Article } from '../../models/article.model';

@Component({
  selector: 'app-article-item',
  imports: [RouterLink],
  templateUrl: './article-item.component.html',
  styleUrl: './article-item.component.scss'
})
export class ArticleItemComponent {
  article = input.required<Article>();
  deleteRequest = output<number>();
  editRequest = output<Article>();

  onDelete() {
    this.deleteRequest.emit(this.article().id);
  }

  onEdit() {
    this.editRequest.emit(this.article());
  }
}