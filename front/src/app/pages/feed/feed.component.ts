import { Component, OnInit } from '@angular/core';
import { ArticleService } from '../../services/article.service';
import { Article } from '../../models/article.model';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss']

})
export class FeedComponent implements OnInit {
  articles: Article[] = [];
  selectedSort: string = 'desc';
  constructor(private articleService: ArticleService) { }

  ngOnInit(): void {
    this.articleService.getFeed().subscribe((data) => {
      this.articles = data;
      this.sortArticles(); 
    });
  }

  onSortChange(): void {
    this.sortArticles();
  }

  private sortArticles(): void {
  this.articles.sort((a, b) => {
    const dateA = new Date(a.date).getTime();
    const dateB = new Date(b.date).getTime();

    if (this.selectedSort === 'asc') {
      return dateA - dateB; // du plus ancien au plus récent
    } else {
      return dateB - dateA; // du plus récent au plus ancien
    }
  });
}

}
