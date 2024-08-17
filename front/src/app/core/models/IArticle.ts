import {IComment} from "./IComment";

export interface IArticle {
  id?: number;
  title: string;
  content: string;
  comments?: Array<IComment>;
  author_id?: number;
  author_name?: string;
  topic_id: number;
  topic_name?: string;
  created_at?: string;
  updated_at?: string;
}
