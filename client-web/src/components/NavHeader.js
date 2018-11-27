import React from 'react';
import Navbar from 'react-bootstrap/lib/Navbar';
import Container from 'react-bootstrap/lib/Container';

class NavHeader extends React.Component {
  render() {
    let username = 'not logged in';
    if (this.props.user) {
      username = this.props.user.username;
    }
    return (
      <Navbar className="navbar">
        <Container>
          <Navbar.Brand>Timeline</Navbar.Brand>
          <Navbar.Collapse className="justify-content-end">
            <Navbar.Text>Signed in as: {username}</Navbar.Text>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    );
  }
}

export default NavHeader;
