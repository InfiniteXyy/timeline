import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import React from 'react';
import { store } from './store';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'moment/locale/zh-cn';

import App from './components/App';

ReactDOM.render(
  <Provider store={store}>
    <BrowserRouter>
      <Switch>
        <Route path="/" component={App} />
      </Switch>
    </BrowserRouter>
  </Provider>,

  document.getElementById('root')
);
