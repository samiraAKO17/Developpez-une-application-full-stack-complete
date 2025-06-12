import { Component, OnInit } from '@angular/core';
import { TopicService } from 'src/app/services/topic.service';
import { ArticleService } from 'src/app/services/article.service';
import { Topic } from 'src/app/models/topic.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-article-create',
  templateUrl: './article-create.component.html',
  styleUrls: ['./article-create.component.scss']
})
export class ArticleCreateComponent implements OnInit {
  topics: Topic[] = [];
  selectedTopicId!: number;
  title = '';
  content = '';

  constructor(
    private topicService: TopicService,
    private articleService: ArticleService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.topicService.getAllTopics().subscribe(topics => this.topics = topics);
  }

  goBack(): void {
    history.back();
  }

  createArticle(): void {
    if (!this.selectedTopicId || !this.title.trim() || !this.content.trim()) return;

    this.articleService.createArticle({
      topic: this.selectedTopicId,
      title: this.title,
      content: this.content
    }).subscribe(() => this.router.navigate(['/feed']));
  }
}
