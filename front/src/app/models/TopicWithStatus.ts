import { Topic } from "./topic.model";

export interface TopicWithStatus extends Topic {
  isSubscribed: boolean;
}
