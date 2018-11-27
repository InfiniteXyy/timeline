import React from 'react';
import Home from './Home';
import Container from 'react-bootstrap/lib/Container';
import NavHeader from './NavHeader';
import connect from 'react-redux/es/connect/connect';
import Row from 'react-bootstrap/lib/Row';
import Col from 'react-bootstrap/lib/Col';
import "./App.css"

const mapStateToProps = state => ({ user: state.common.currentUser });
const mapDispatchToProps = dispatch => ({});

class App extends React.Component {
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
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App);
