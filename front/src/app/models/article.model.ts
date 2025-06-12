import { Topic } from "./topic.model.js";
import { User } from "./user.model.js";

export interface Article {
  id: number;
  title: string;
  content: string;
  date: Date;
  topic: number;
  user: number;
}
