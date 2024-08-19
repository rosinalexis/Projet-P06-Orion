import {ITopic} from "./ITopic";

export interface IUser {
  created_at?: string;
  email?: string;
  id?: number;
  subscriptions?: Array<ITopic>;
  updated_at?: string;
  username: string;
}
