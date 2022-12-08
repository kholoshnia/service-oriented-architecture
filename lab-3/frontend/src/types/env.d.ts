/**
 * Types for environmental variables.
 * See .env files for more information.
 */
declare namespace NodeJS {
  interface ProcessEnv {
    readonly NODE_ENV: 'development' | 'production' | 'test';
    readonly PUBLIC_URL: string;
    readonly REACT_APP_SHOP_API_URL: string;
    readonly REACT_APP_STORAGE_API_URL: string;
  }
}
