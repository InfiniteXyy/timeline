import {
  ASYNC_START,
  ASYNC_END,
  CLOSE_LOGIN_DIALOG,
  HOME_PAGE_LOADED,
  HOME_PAGE_UNLOADED,
  LOGIN,
  OPEN_LOGIN_DIALOG
} from '../constants/actionTypes';

const defaultState = {
  loginDialogShow: false,
  inProgress: false
};
/** @namespace action.payload.errors */
export default (state = defaultState, action) => {
  switch (action.type) {
    case HOME_PAGE_LOADED:
      return { ...state };
    case HOME_PAGE_UNLOADED:
      return {};
    case OPEN_LOGIN_DIALOG:
      return { ...state, loginDialogShow: true };
    case CLOSE_LOGIN_DIALOG:
      return { ...state, loginDialogShow: false };
    case LOGIN:
      if (!action.error) return { ...state, loginDialogShow: false };
      // TODO: 用更加好看的效果来实现提示吧！
      for (let key in action.payload.errors) {
        if (action.payload.errors.hasOwnProperty(key)) {
          alert(key + ": " + action.payload.errors[key]);
        }
      }
      return state;
    case ASYNC_START:
      if (action.subtype === LOGIN) return { ...state, inProgress: true };
      break;
    case ASYNC_END:
      return { ...state, inProgress: false };
    default:
      return state;
  }
  return state;
};
