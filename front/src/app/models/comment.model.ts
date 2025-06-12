import { Article } from "./article.model";
import { User } from "./user.model";

export interface Comment {
  id: number;
  comment: string;
  date: Date;
  article: number;
  user: number|string;
}
