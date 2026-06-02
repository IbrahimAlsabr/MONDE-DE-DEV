export interface ProfileSubscription {
  id: number;
  name: string;
  description: string;
}

export interface Profile {
  id: number;
  email: string;
  username: string;
  subscriptions: ProfileSubscription[];
}

export interface ProfileUpdateRequest {
  email: string;
  username: string;
  password?: string;
  confirmPassword?: string;
}
