export type AuthUser = {
	id: number;
	email: string;
	username: string;
};

export type AuthResponse = {
	accessToken: string;
	refreshToken: string;
	tokenType: string;
	user: AuthUser;
};

export type LoginRequest = {
	identifier: string; // email OR username
	password: string;
};

export type SignupRequest = {
	email: string;
	username: string;
	password: string;
};

