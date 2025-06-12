import { Component, OnInit } from '@angular/core';
import { Topic } from 'src/app/models/topic.model';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {
  topics: (Topic & { isSubscribed: boolean })[] = [];

  constructor(private topicService: TopicService) {}

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics(): void {
    this.topicService.getAllTopics().subscribe(allTopics => {
      this.topicService.getUserTopics().subscribe(userTopics => {
        const userTopicIds = new Set(userTopics.map(t => t.id));
        this.topics = allTopics.map(t => ({
          ...t,
          isSubscribed: userTopicIds.has(t.id)
        }));
      });
    });
  }

  toggleSubscription(topic: Topic & { isSubscribed: boolean }) {
    if (topic.isSubscribed) {
      this.topicService.unsubscribeFromTopic(topic.id).subscribe(() => {
        topic.isSubscribed = false;
      });
    } else {
      this.topicService.subscribeToTopic(topic.id).subscribe(() => {
        topic.isSubscribed = true;
      });
    }
    this.loadTopics();
  }
}
