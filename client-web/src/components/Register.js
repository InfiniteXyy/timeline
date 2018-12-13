import React from 'react';
import { REGISTER } from '../constants/actionTypes';
import agent from '../agent';
import { connect } from 'react-redux';
import ErrorList from './common/ErrorList';

class Register extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      password: '',
      email: '',
      username: ''
    };
    this.setPassword = ev => {
      this.setState({ password: ev.target.value });
    };
    this.setEmail = ev => {
      this.setState({ email: ev.target.value });
    };
    this.setUsername = ev => {
      this.setState({ username: ev.target.value });
    };
    this.handleSubmit = ev => {
      ev.preventDefault();
      props.onSubmitRegister(this.state.email, this.state.username, this.state.password);
    };
  }

  render() {
    return (
      <div className="login-form" onSubmit={this.handleSubmit}>
        <div className="main-div">
          <div className="panel">
            <h2>注册新的账户</h2>
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
                type="text"
                className="form-control"
                id="inputUsername"
                placeholder="用户名"
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

            <button type="submit" className="btn btn-primary" style={{ marginTop: 30 }}>
              {this.props.inProgress ? <div className="loader " /> : <span>注册</span>}
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
  onSubmitRegister: (email, username, password) =>
    dispatch({ type: REGISTER, payload: agent.Auth.register(email, username, password) })
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Register);
