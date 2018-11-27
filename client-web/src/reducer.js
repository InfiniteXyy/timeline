import { combineReducers } from 'redux';
import home from './reducers/home';
import common from './reducers/common';

export default combineReducers({
  home,
  common
});
