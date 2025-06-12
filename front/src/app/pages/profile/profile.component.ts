import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';
import { Topic } from 'src/app/models/topic.model';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user!: User;
  topics: Topic[] = [];

  constructor(private userService: UserService, private topicService: TopicService) {}

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(user => this.user = user);
    this.topicService.getUserTopics().subscribe(topics => this.topics = topics);
  }

  save(): void {
    this.userService.updateProfile(this.user).subscribe();
  }

  unsubscribe(topicId: number): void {
    this.topicService.unsubscribeFromTopic(topicId).subscribe(() => {
      this.topics = this.topics.filter(t => t.id !== topicId);
    });
  }
}
