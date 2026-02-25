import { Component, inject, input, OnInit, output, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ShopService } from '../../services/shop.service';
import { Article } from '../../models/article.model';

@Component({
  selector: 'app-add-article',
  imports: [FormsModule],
  templateUrl: './add-article.component.html',
  styleUrl: './add-article.component.scss'
})
export class AddArticleComponent implements OnInit {
  private shopService = inject(ShopService);
  
  close = output<void>();
  dataToEdit = input<Article | null>(null);
  
  categories = this.shopService.categories;
  
  enteredName = signal('');
  enteredCategoryId = signal(this.categories[0].id);
  enteredPrice = signal(0);
  enteredDate = signal(new Date().toISOString().split('T')[0]);

  ngOnInit() {
    const data = this.dataToEdit();
    if (data) {
      this.enteredName.set(data.name);
      this.enteredCategoryId.set(data.categoryId);
      this.enteredPrice.set(data.price);
      this.enteredDate.set(data.expirationDate.split('T')[0]);
    }
  }

  onCancel() {
    this.close.emit();
  }

  onSubmit() {
    const priceVal = Number(this.enteredPrice());
    const catIdVal = Number(this.enteredCategoryId());
    const nameVal = this.enteredName();
    const dateVal = this.enteredDate();
    
    const catObj = this.categories.find(c => c.id === catIdVal);

    const data = this.dataToEdit();
    
    if (data) {
      const updatedArticle: Article = {
        ...data,
        name: nameVal,
        categoryId: catIdVal,
        category: catObj,
        price: priceVal,
        expirationDate: dateVal
      };
      this.shopService.updateArticle(updatedArticle);
    } else {
      this.shopService.addArticle(
        nameVal,
        catIdVal,
        priceVal,
        dateVal
      );
    }
    this.close.emit();
  }
}