import { Component, Input, OnInit } from '@angular/core';
import { Article } from 'src/app/models/article.model';
import { UserService } from 'src/app/services/user.service';
import { TopicService } from 'src/app/services/topic.service';
import { User } from 'src/app/models/user.model';
import { Topic } from 'src/app/models/topic.model';

@Component({
  selector: 'app-article-card',
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.scss']
})
export class ArticleCardComponent implements OnInit {
  @Input() article!: Article;

  author?: User;
  topic?: Topic;

  constructor(
    private userService: UserService,
    private topicService: TopicService
  ) {}

  ngOnInit(): void {
    this.userService.getUserById(this.article.user).subscribe(user => this.author = user);
    this.topicService.getTopicById(this.article.topic).subscribe(topic => this.topic = topic);
  }
}
