import superagentPromise from 'superagent-promise';
import _superagent from 'superagent';

const superagent = superagentPromise(_superagent, global.Promise);

const API_ROOT = '/api';

const encode = encodeURIComponent;
const responseBody = res => res.body;

let token = null;

const tokenPlugin = req => {
  if (token) {
    req.set('authorization', `Token ${token}`);
  }
};

const requests = {
  del: url =>
    superagent
      .del(`${API_ROOT}${url}`)
      .use(tokenPlugin)
      .then(responseBody),
  get: url =>
    superagent
      .get(`${API_ROOT}${url}`)
      .use(tokenPlugin)
      .then(responseBody),
  put: (url, body) =>
    superagent
      .put(`${API_ROOT}${url}`, body)
      .use(tokenPlugin)
      .then(responseBody),
  post: (url, body) =>
    superagent
      .post(`${API_ROOT}${url}`, body)
      .use(tokenPlugin)
      .then(responseBody)
};

const Message = {
  get: () => requests.get('/messages'),
  delete: id => requests.del(`/messages/${encode(id)}`),
  update: (id, content, time) => requests.put('/messages', { id, content, time }),
  add: (content, time) => requests.post('/messages', { content, time })
};

const Auth = {
  login: (username, password) => requests.post('/auth/login', { username, password }),
  register: (username, password) => requests.post('/users', { username, password })
};

export default {
  Message,
  Auth,
  setToken: _token => {
    token = _token;
  }
};
