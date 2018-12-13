import React from 'react';
import { APP_LOAD } from '../constants/actionTypes';

import Container from 'react-bootstrap/lib/Container';
import Row from 'react-bootstrap/lib/Row';
import Col from 'react-bootstrap/lib/Col';
import { connect } from 'react-redux';

import agent from '../agent';
import Home from './Home';
import NavHeader from './NavHeader';

import './App.css';

class App extends React.Component {
  // 在启动 App 查看是否有保存着的token
  componentWillMount() {
    const token = window.localStorage.getItem('jwt');
    if (token) {
      agent.setToken(token);
    }
    this.props.onLoad(token ? agent.Auth.current() : null, token);
  }
  render() {
    return (
      <div className="root">
        <NavHeader user={this.props.user} />
        <Container>
          <Row>
            <Col cols={4} className="column-container">
              <Home />
            </Col>
          </Row>
        </Container>
      </div>
    );
  }
}
const mapDispatchToProps = dispatch => ({
  onLoad: (payload, token) => dispatch({ type: APP_LOAD, payload, token })
});

const mapStateToProps = state => {
  return {};
};
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App);
