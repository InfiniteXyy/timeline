import { applyMiddleware, createStore } from 'redux';
import { createLogger } from 'redux-logger';
import { composeWithDevTools } from 'redux-devtools-extension/developmentOnly';
import reducer from './reducer';
import { promiseMiddleware, localStorageMiddleware } from './middleware';

const getMiddleware = () => {
  if (process.env.NODE_ENV === 'production') {
    return applyMiddleware(promiseMiddleware, localStorageMiddleware);
  } else {
    // Enable additional logging in non-production environments.
    return applyMiddleware(createLogger(), promiseMiddleware, localStorageMiddleware);
  }
};

export const store = createStore(reducer, composeWithDevTools(getMiddleware()));
