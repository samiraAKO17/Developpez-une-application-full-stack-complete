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
  selectedSort: string = 'date';

  constructor(private articleService: ArticleService) { }

  ngOnInit(): void {
    this.articleService.getFeed().subscribe((data) => {
      this.articles = data;
      this.sortArticles(); // Tri initial
    });
  }

  onSortChange(): void {
    this.sortArticles();
  }

  private sortArticles(): void {
    this.articles.sort((a, b) => {
      if (this.selectedSort === 'title') {
        return a.title.localeCompare(b.title);
      } else if (this.selectedSort === 'date') {
        return new Date(b.date).getTime() - new Date(a.date).getTime(); // plus récent en premier
      }
      return 0;
    });
  }
}
