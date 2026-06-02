export interface TopicSummary {
  id: number;
  name: string;
}

export interface UserSummary {
  id: number;
  username: string;
}

export interface PostComment {
  id: number;
  content: string;
  author: UserSummary;
  createdAt: string;
}

export interface Post {
  id: number;
  topic: TopicSummary;
  title: string;
  content: string;
  author: UserSummary;
  createdAt: string;
  comments: PostComment[];
}

export interface PostCreateRequest {
  topicId: number;
  title: string;
  content: string;
}
