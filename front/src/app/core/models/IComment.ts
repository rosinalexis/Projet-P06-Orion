export interface IComment {
  id?: number;
  article_id: number;
  author_id?: number;
  author_name?: string;
  content: string;
  created_at?: string;
  updated_at?: string;
}
