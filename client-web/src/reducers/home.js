import { ASYNC_START, HOME_PAGE_LOADED, HOME_PAGE_UNLOADED } from '../constants/actionTypes';

const defaultState = {
  inProgress: false,
  messages: [],
};
export default (state = defaultState, action) => {
  switch (action.type) {
    case HOME_PAGE_LOADED:
      return {
        ...state,
        messages: action.payload.data,
        inProgress: false
      };
    case HOME_PAGE_UNLOADED:
      return {};
    case ASYNC_START:
      return { ...state, inProgress: true };
    default:
      return state;
  }
};
