import React from 'react';
import { LOGIN } from '../constants/actionTypes';
import agent from '../agent';
import { connect } from 'react-redux';
class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      password: '',
      username: ''
    };
    this.setPassword = ev => {
      this.setState({ password: ev.target.value });
    };
    this.setUsername = ev => {
      this.setState({ username: ev.target.value });
    };
    this.handleSubmit = ev => {
      ev.preventDefault();
      props.onSubmitLogin(this.state.username, this.state.password);
      props.handleClose();
    };
  }

  render() {
    return (
      <div className="login-form" onSubmit={this.handleSubmit}>
        <div className="main-div">
          <div className="panel">
            <h2>登录你的账户</h2>
            <p>请输入邮箱地址和对应的密码</p>
          </div>
          <form id="Login">
            <div className="form-group">
              <input
                type="text"
                className="form-control"
                id="inputEmail"
                placeholder="邮箱地址"
                onChange={this.setUsername}
              />
            </div>

            <div className="form-group">
              <input
                type="password"
                className="form-control"
                id="inputPassword"
                placeholder="密码"
                onChange={this.setPassword}
              />
            </div>
            <div className="forgot">
              <a href="">忘记密码？</a>
            </div>
            <button type="submit" className="btn btn-primary">
              登录
            </button>
          </form>
        </div>
      </div>
    );
  }
}
const mapDispatchToProps = dispatch => ({
  onSubmitLogin: (username, password) => dispatch({ type: LOGIN, payload: agent.Auth.login(username, password) })
});

export default connect(
  () => ({}),
  mapDispatchToProps
)(Login);
