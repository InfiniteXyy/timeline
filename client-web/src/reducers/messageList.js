import { ASYNC_END, ASYNC_START, HOME_PAGE_LOADED, HOME_PAGE_UNLOADED, ADD_MESSAGE } from '../constants/actionTypes';

const defaultState = {
  inProgress: false
};

export default (state = defaultState, action) => {
  switch (action.type) {
    case HOME_PAGE_LOADED:
      return { ...state, messages: action.payload.messages };
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
        messages: action.error ? null : [...state.messages, action.payload.message]
      };
    default:
      return state;
  }
  return state;
};
