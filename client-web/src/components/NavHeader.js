import React from 'react';
import Navbar from 'react-bootstrap/lib/Navbar';
import Container from 'react-bootstrap/lib/Container';
import Image from 'react-bootstrap/lib/Image';
import Dropdown from 'react-bootstrap/lib/Dropdown';
import { connect } from 'react-redux';
import {
  CLOSE_LOGIN_DIALOG,
  CLOSE_PROFILE_DIALOG,
  CLOSE_REGISTER_DIALOG,
  LOGOUT,
  OPEN_LOGIN_DIALOG,
  OPEN_PROFILE_DIALOG,
  OPEN_REGISTER_DIALOG
} from '../constants/actionTypes';
import Login from './Login';
import Modal from 'react-bootstrap/lib/Modal';
import ModalHeader from 'react-bootstrap/lib/ModalHeader';
import CustomToggle from './common/CustomToggle';
import Register from './Register';
import { defaultAvatar } from './App';
import Profile from './Profile';

class NavHeader extends React.Component {
  render() {
    let user = this.props.currentUser;
    let avatarUrl;
    if (user && user.image) {
      avatarUrl = user.image;
    } else {
      avatarUrl = defaultAvatar;
    }
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
          <ModalHeader className={'modal-header'} closeButton={true} />
          <Login handleClose={this.props.onCloseLoginDialog} />
        </Modal>

        <Modal show={this.props.registerDialogShow} onHide={this.props.onCloseRegisterDialog}>
          <ModalHeader className={'modal-header'} closeButton={true} />
          <Register handleClose={this.props.onCloseRegisterDialog} />
        </Modal>

        <Modal show={this.props.profileDialogShow} onHide={this.props.onCloseProfileDialog}>
          <ModalHeader className={'modal-header'} closeButton={true} />
          <Profile />
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
          <p className="full-name">{username}</p>
          <p className="email">@{username}</p>
        </div>

        <div className="dropdown-divider" />
        <Dropdown.Item onClick={this.props.onOpenProfileDialog}>
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
        <Dropdown.Item onClick={this.props.onOpenRegisterDialog}>
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
  loginDialogShow: state.home.loginDialogShow,
  registerDialogShow: state.home.registerDialogShow,
  profileDialogShow: state.home.profileDialogShow
});

const mapDispatchToProps = dispatch => ({
  onClickLogout: () => dispatch({ type: LOGOUT }),
  onOpenLoginDialog: () => dispatch({ type: OPEN_LOGIN_DIALOG }),
  onCloseLoginDialog: () => dispatch({ type: CLOSE_LOGIN_DIALOG }),
  onOpenRegisterDialog: () => dispatch({ type: OPEN_REGISTER_DIALOG }),
  onCloseRegisterDialog: () => dispatch({ type: CLOSE_REGISTER_DIALOG }),
  onOpenProfileDialog: () => dispatch({ type: OPEN_PROFILE_DIALOG }),
  onCloseProfileDialog: () => dispatch({ type: CLOSE_PROFILE_DIALOG })
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NavHeader);
