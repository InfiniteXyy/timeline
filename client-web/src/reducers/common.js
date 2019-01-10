import { APP_LOAD, LOGIN, LOGOUT } from '../constants/actionTypes';

export default (state = {}, action) => {
  switch (action.type) {
    case APP_LOAD:
      return {
        ...state,
        token: action.token || null,
        currentUser: action.payload ? action.payload.user : null
      };
    case LOGIN:
      return {
        ...state,
        token: action.error ? null : action.payload.user.token,
        currentUser: action.error ? null : action.payload.user
      };
    case LOGOUT:
      return { ...state, token: null, currentUser: null };
    default:
      return state;
  }
};
