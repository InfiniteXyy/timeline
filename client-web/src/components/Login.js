import React from 'react';
import { LOGIN } from '../constants/actionTypes';
import agent from '../agent';
import { connect } from 'react-redux';
import ErrorList from './common/ErrorList';
class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      password: '',
      email: ''
    };
    this.setPassword = ev => {
      this.setState({ password: ev.target.value });
    };
    this.setEmail = ev => {
      this.setState({ email: ev.target.value });
    };
    this.handleSubmit = ev => {
      ev.preventDefault();
      props.onSubmitLogin(this.state.email, this.state.password);
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
          <ErrorList errors={this.props.errors} />
          <form id="Login">
            <div className="form-group">
              <input
                type="email"
                className="form-control"
                id="inputEmail"
                placeholder="邮箱地址"
                onChange={this.setEmail}
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
            <button type="submit" className="btn btn-primary">
              {this.props.inProgress ? <div className="loader " /> : <span>登录</span>}
            </button>
          </form>
        </div>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  inProgress: state.home.inProgress,
  errors: state.home.errors
});

const mapDispatchToProps = dispatch => ({
  onSubmitLogin: (email, password) => dispatch({ type: LOGIN, payload: agent.Auth.login(email, password) })
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Login);
