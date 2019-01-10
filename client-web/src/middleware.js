import agent from './agent';
import { ASYNC_END, ASYNC_START, LOGIN, LOGOUT, REGISTER } from './constants/actionTypes';

function isPromise(v) {
  return v && typeof v.then === 'function';
}
const promiseMiddleware = store => next => action => {
  // 如果是Promise请求，就调用then方法，并且状态变为正在同步
  if (isPromise(action.payload)) {
    store.dispatch({ type: ASYNC_START, subtype: action.type });

    action.payload.then(
      res => {
        action.payload = res;
        store.dispatch({ type: ASYNC_END, promise: action.payload });
        store.dispatch(action);
      },
      error => {
        action.error = true;
        action.payload = error.response.body;
        store.dispatch({ type: ASYNC_END, promise: action.payload });
        store.dispatch(action);
      }
    );
    return;
  }
  // 其它action就直接跳过
  next(action);
};

const localStorageMiddleware = () => next => action => {
  if (action.type === REGISTER || action.type === LOGIN) {
    if (!action.error) {
      window.localStorage.setItem('jwt', action.payload.user.token);
      agent.setToken(action.payload.user.token);
    }
  } else if (action.type === LOGOUT) {
    window.localStorage.setItem('jwt', '');
    agent.setToken(null);
  }

  next(action);
};

export { promiseMiddleware, localStorageMiddleware };
