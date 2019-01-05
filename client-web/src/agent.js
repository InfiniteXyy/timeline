import superagentPromise from 'superagent-promise';
import _superagent from 'superagent';

const superagent = superagentPromise(_superagent, global.Promise);

const API_ROOT = '/api';

const encode = encodeURIComponent;
const responseBody = res => res.body;

let token = null;

const tokenPlugin = req => {
  if (token) {
    req.set('authorization', `Bearer ${token}`);
  }
};

const uploadToken = req => {
  req.set('authorization', 'bearer ILoveInfinitex.cn');
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
  add: (body, imageUrl = '') => requests.post('/messages/', { message: { body, imageUrl } }),
  loadMore: from => requests.get(`/messages/?from=${encode(from)}`)
};

const Auth = {
  current: () => requests.get('/user'),
  save: user => requests.put('/user', { user }),
  login: (email, password) => requests.post('/users/login', { user: { email, password } }),
  register: (email, username, password) =>
    requests.post('/users', { user: { email, username, password } })
};

const Profile = {
  get: username => requests.get(`/profiles/${encode(username)}`)
};

const Upload = {
  image: body =>
    superagent
      .post(`/cdn/api/upload`, body)
      .use(uploadToken)
      .then(responseBody)
};

export default {
  Message,
  Auth,
  Profile,
  Upload,
  setToken: _token => {
    token = _token;
  }
};
