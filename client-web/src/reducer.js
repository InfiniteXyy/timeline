import { combineReducers } from 'redux';
import home from './reducers/home';
import common from './reducers/common';
import messageList from './reducers/messageList';

export default combineReducers({
  home,
  common,
  messageList
});
