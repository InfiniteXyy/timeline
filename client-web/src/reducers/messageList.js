import {
  ASYNC_END,
  ASYNC_START,
  HOME_PAGE_LOADED,
  HOME_PAGE_UNLOADED,
  ADD_MESSAGE,
  LOAD_MORE
} from '../constants/actionTypes';

const defaultState = {
  inProgress: false,
  messages: []
};

export default (state = defaultState, action) => {
  switch (action.type) {
    case HOME_PAGE_LOADED:
      return { ...state, messages: action.error ? null : action.payload.messages };
    case HOME_PAGE_UNLOADED:
      return {};
    case ASYNC_START:
      if (action.subtype === HOME_PAGE_LOADED) {
        return { ...state, inProgress: true };
      }
      break;
    case ASYNC_END:
      return { ...state, inProgress: false };
    case ADD_MESSAGE:
      return {
        ...state,
        messages: action.error ? null : [action.payload.message, ...state.messages]
      };
    case LOAD_MORE:
      return {
        ...state,
        messages: action.error ? state.messages : [...state.messages, ...action.payload.messages]
      };
    default:
      return state;
  }
  return state;
};
