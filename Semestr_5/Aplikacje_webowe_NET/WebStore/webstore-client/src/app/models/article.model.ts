export interface Category {
  id: number;
  name: string;
}

export interface Article {
  id: number;
  name: string;
  price: number;
  expirationDate: string;
  imageName?: string | null;
  categoryId: number;
  category?: Category | null;
}