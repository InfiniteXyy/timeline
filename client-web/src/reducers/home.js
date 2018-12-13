import {
  ASYNC_START,
  ASYNC_END,
  CLOSE_LOGIN_DIALOG,
  HOME_PAGE_LOADED,
  HOME_PAGE_UNLOADED,
  LOGIN,
  OPEN_LOGIN_DIALOG,
  OPEN_REGISTER_DIALOG,
  CLOSE_REGISTER_DIALOG,
  REGISTER
} from '../constants/actionTypes';

const defaultState = {
  loginDialogShow: false,
  registerDialogShow: false,
  inProgress: false,
  errors: null
};
export default (state = defaultState, action) => {
  switch (action.type) {
    case HOME_PAGE_LOADED:
      return { ...state };
    case HOME_PAGE_UNLOADED:
      return {};
    case OPEN_LOGIN_DIALOG:
      return { ...state, loginDialogShow: true };
    case CLOSE_LOGIN_DIALOG:
      return { ...state, loginDialogShow: false, errors: null };
    case OPEN_REGISTER_DIALOG:
      return { ...state, registerDialogShow: true };
    case CLOSE_REGISTER_DIALOG:
      return { ...state, registerDialogShow: false, errors: null };
    case LOGIN:
      return { ...state, loginDialogShow: action.error, errors: action.error ? action.payload.errors : null };
    case REGISTER:
      return { ...state, registerDialogShow: action.error, errors: action.error ? action.payload.errors : null };
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
