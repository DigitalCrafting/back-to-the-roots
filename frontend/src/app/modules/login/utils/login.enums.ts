export type LoginStatusEnum = 'LOGGED_OUT' | 'LOGIN_FAILED' | 'LOGGED_IN' | 'NOT_EXISTS';
export const LoginStatusEnum = {
    LOGGED_OUT: 'LOGGED_OUT' as LoginStatusEnum,
    LOGGED_IN: 'LOGGED_IN' as LoginStatusEnum,
    LOGIN_FAILED: 'LOGIN_FAILED' as LoginStatusEnum,
    NOT_EXISTS: 'NOT_EXISTS' as LoginStatusEnum
};
