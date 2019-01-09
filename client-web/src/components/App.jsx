import React from 'react';
import { func, Requireable as object } from 'prop-types';

import Container from 'react-bootstrap/lib/Container';
import Row from 'react-bootstrap/lib/Row';
import Col from 'react-bootstrap/lib/Col';
import { connect } from 'react-redux';
import { APP_LOAD } from '../constants/actionTypes';

import agent from '../agent';
import Home from './Home';
import NavHeader from './NavHeader';

import './App.css';

class App extends React.Component {
  // 在启动 App 查看是否有保存着的token
  componentWillMount() {
    const token = window.localStorage.getItem('jwt');
    const { onLoad } = this.props;
    if (token) {
      agent.setToken(token);
    }
    onLoad(token ? agent.Auth.current() : null, token);
  }

  render() {
    const { user } = this.props;
    return (
      <div className="root">
        <NavHeader user={user} />
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

App.propTypes = {
  onLoad: func.isRequired,
  user: object.isRequired
};
const mapDispatchToProps = dispatch => ({
  onLoad: (payload, token) => dispatch({ type: APP_LOAD, payload, token })
});

const mapStateToProps = () => ({});
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App);
