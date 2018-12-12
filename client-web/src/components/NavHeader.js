import React from 'react';
import Navbar from 'react-bootstrap/lib/Navbar';
import Container from 'react-bootstrap/lib/Container';
import Image from 'react-bootstrap/lib/Image';
import Dropdown from 'react-bootstrap/lib/Dropdown';
import { connect } from 'react-redux';
import { CLOSE_LOGIN_DIALOG, LOGOUT, OPEN_LOGIN_DIALOG } from '../constants/actionTypes';
import Login from './Login';
import Modal from 'react-bootstrap/lib/Modal';
import ModalHeader from 'react-bootstrap/lib/ModalHeader';
import CustomToggle from './common/CustomToggle';

class NavHeader extends React.Component {
  render() {
    let user = this.props.currentUser;
    let avatarUrl = user
      ? 'data:image/jpeg;base64,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'
      : 'https://i0.wp.com/ebus.ca/wp-content/uploads/2017/08/profile-placeholder.jpg?ssl=1';
    return (
      <Navbar className="navbar">
        <Container>
          <Navbar.Brand>Timeline</Navbar.Brand>
          <Navbar.Collapse className="justify-content-end">
            <Dropdown alignRight id="dropdown-avatar-components">
              <Dropdown.Toggle as={CustomToggle} id="dropdown-custom-components">
                <Image className="avatar" src={avatarUrl} roundedCircle />
              </Dropdown.Toggle>
              {user ? this.renderLoggedInMenu(user) : this.renderMenu()}
            </Dropdown>
          </Navbar.Collapse>
        </Container>
        <Modal show={this.props.loginDialogShow} onHide={this.props.onCloseLoginDialog}>
          <ModalHeader className={'modal-header'}>
            <button
              type="button"
              className="close"
              data-dismiss="modal"
              aria-label="Close"
              onClick={this.props.onCloseLoginDialog}
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </ModalHeader>
          <Login handleClose={this.props.onCloseLoginDialog} />
        </Modal>
      </Navbar>
    );
  }

  /**
   * @param user
   * @returns Dropdown Menu View for logged in users
   */
  renderLoggedInMenu = user => {
    let username = user.username;
    return (
      <Dropdown.Menu>
        <div className="profile-container">
          <p className="fullname">{username}</p>
          <p className="email">@{username}</p>
        </div>

        <div className="dropdown-divider" />
        <Dropdown.Item eventKey="1">
          <span>
            <i className="fa fa-user-o fa-fw" />
            个人信息
          </span>
        </Dropdown.Item>
        <Dropdown.Item onClick={this.props.onClickLogout}>
          <span>
            <i className="fa fa-sign-out fa-fw" />
            退出登录
          </span>
        </Dropdown.Item>
      </Dropdown.Menu>
    );
  };

  /**
   * @returns Plain Dropdown Menu View
   */
  renderMenu = () => {
    return (
      <Dropdown.Menu>
        <Dropdown.Item onClick={this.props.onOpenLoginDialog}>
          <span>
            <i className="fa fa-sign-in fa-fw" />
            登录
          </span>
        </Dropdown.Item>
        <Dropdown.Item onClick={this.props.onOpenLoginDialog}>
          <span>
            <i className="fa fa-vcard-o fa-fw" />
            注册
          </span>
        </Dropdown.Item>
      </Dropdown.Menu>
    );
  };
}

const mapStateToProps = state => ({
  currentUser: state.common.currentUser,
  loginDialogShow: state.home.loginDialogShow
});

const mapDispatchToProps = dispatch => ({
  onClickLogout: () => dispatch({ type: LOGOUT }),
  onOpenLoginDialog: () => dispatch({ type: OPEN_LOGIN_DIALOG }),
  onCloseLoginDialog: () => dispatch({ type: CLOSE_LOGIN_DIALOG })
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NavHeader);
