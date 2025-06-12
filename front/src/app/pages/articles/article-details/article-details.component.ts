import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Article } from 'src/app/models/article.model';
import { Comment } from 'src/app/models/comment.model';
import { ArticleService } from 'src/app/services/article.service';
import { CommentService } from 'src/app/services/comment.service';
import { UserService } from 'src/app/services/user.service';
import { TopicService } from 'src/app/services/topic.service';
import { User } from 'src/app/models/user.model';
import { Topic } from 'src/app/models/topic.model';

@Component({
  selector: 'app-article-details',
  templateUrl: './article-details.component.html',
  styleUrls: ['./article-details.component.scss']
})
export class ArticleDetailsComponent implements OnInit {

  article!: Article;
  comments: Comment[] = [];
  newComment = '';
  author!: User;
  articleTopic!: Topic;

  constructor(
    private articleService: ArticleService,
    private commentService: CommentService,
    private userService: UserService,
    private topicService: TopicService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const articleId = Number(params.get('id'));
      this.loadArticle(articleId);
      this.loadComments(articleId);
    });
  }

  loadArticle(id: number): void {
    this.articleService.getArticleById(id).subscribe({
      next: (article) => {
        this.article = article;

        this.userService.getUserById(article.user).subscribe({
          next: user => this.author = user,
          error: err => console.error('Erreur utilisateur', err)
        });

        this.topicService.getTopicById(article.topic).subscribe({
          next: topic => this.articleTopic = topic,
          error: err => console.error('Erreur topic', err)
        });
      },
      error: err => console.error('Erreur article', err)
    });
  }

  loadComments(articleId: number): void {
    this.commentService.getCommentsByArticle(articleId).subscribe({
      next: (comments) => {
        this.comments = comments;
        this.enrichCommentsWithUsers();
      },
      error: (err) => console.error('Erreur chargement commentaires', err)
    });
  }

  enrichCommentsWithUsers(): void {
    const userIds = Array.from(
      new Set(
        this.comments
          .map(comment => comment.user)
          .filter(id => typeof id === 'number')
      )
    ) as number[];

    userIds.forEach(userId => {
      this.userService.getUserById(userId).subscribe({
        next: (user) => {
          // Remplace tous les commentaires avec cet ID par le nom du user
          this.comments.forEach(comment => {
            if (comment.user === userId) {
              comment.user = user.name;
            }
          });
        },
        error: err => console.error(`Erreur user ${userId}`, err)
      });
    });
  }

  submitComment(): void {
    if (!this.newComment.trim()) return;

    const commentToAdd = {
      comment: this.newComment,
      article: this.article.id
    };

    this.commentService.addComment(commentToAdd).subscribe({
      next: (comment) => {
        this.comments.push(comment);
        this.newComment = '';
        this.enrichCommentsWithUsers();
      },
      error: (err) => console.error('Erreur ajout commentaire', err)
    });
  }

  goBack(): void {
    history.back();
  }
}
