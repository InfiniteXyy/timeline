import React from 'react';
import Navbar from 'react-bootstrap/lib/Navbar';
import Container from 'react-bootstrap/lib/Container';
import Image from 'react-bootstrap/lib/Image';
import Dropdown from 'react-bootstrap/lib/Dropdown';
import Modal from 'react-bootstrap/lib/Modal';
import ModalHeader from 'react-bootstrap/lib/ModalHeader';
import { connect } from 'react-redux';
import { object, bool, func } from 'prop-types';
import {
  CLOSE_LOGIN_DIALOG,
  CLOSE_REGISTER_DIALOG,
  LOGOUT,
  OPEN_LOGIN_DIALOG,
  OPEN_REGISTER_DIALOG
} from '../constants/actionTypes';
import Login from './Login';

import CustomToggle from './common/CustomToggle';
import Register from './Register';

import defaultValue from '../constants/defaultValue';

class NavHeader extends React.Component {
  constructor(props) {
    super(props);
    this.renderLoggedInMenu.bind(this);
  }

  renderLoggedInMenu(user) {
    const { username } = user;
    const { onClickLogout } = this.props;
    return (
      <Dropdown.Menu>
        <div className="profile-container">
          <p className="full-name">{username}</p>
          <p className="email">{`@${username}`}</p>
        </div>

        <div className="dropdown-divider" />
        <Dropdown.Item onClick={onClickLogout}>
          <span>
            <i className="fa fa-sign-out fa-fw" />
            退出登录
          </span>
        </Dropdown.Item>
      </Dropdown.Menu>
    );
  }

  renderMenu() {
    const { onOpenLoginDialog, onOpenRegisterDialog } = this.props;
    return (
      <Dropdown.Menu>
        <Dropdown.Item onClick={onOpenLoginDialog}>
          <span>
            <i className="fa fa-sign-in fa-fw" />
            登录
          </span>
        </Dropdown.Item>
        <Dropdown.Item onClick={onOpenRegisterDialog}>
          <span>
            <i className="fa fa-vcard-o fa-fw" />
            注册
          </span>
        </Dropdown.Item>
      </Dropdown.Menu>
    );
  }

  render() {
    const {
      currentUser,
      loginDialogShow,
      registerDialogShow,
      onCloseLoginDialog,
      onCloseRegisterDialog
    } = this.props;
    let avatarUrl;
    if (currentUser && currentUser.image) {
      avatarUrl = currentUser.image;
    } else {
      avatarUrl = defaultValue.avatar;
    }
    return (
      <Navbar className="navbar">
        <Container>
          <Navbar.Brand>时间线</Navbar.Brand>
          <Navbar.Collapse className="justify-content-end">
            <Dropdown alignRight id="dropdown-avatar-components">
              <Dropdown.Toggle as={CustomToggle} id="dropdown-custom-components">
                <Image className="avatar" src={avatarUrl} roundedCircle />
              </Dropdown.Toggle>
              {currentUser ? this.renderLoggedInMenu(currentUser) : this.renderMenu()}
            </Dropdown>
          </Navbar.Collapse>
        </Container>
        <Modal show={loginDialogShow} onHide={onCloseLoginDialog}>
          <ModalHeader className="modal-header" closeButton />
          <Login handleClose={onCloseLoginDialog} />
        </Modal>

        <Modal show={registerDialogShow} onHide={onCloseRegisterDialog}>
          <ModalHeader className="modal-header" closeButton />
          <Register handleClose={onCloseRegisterDialog} />
        </Modal>
      </Navbar>
    );
  }
}

NavHeader.propTypes = {
  onClickLogout: func.isRequired,
  onOpenLoginDialog: func.isRequired,
  onCloseLoginDialog: func.isRequired,
  onOpenRegisterDialog: func.isRequired,
  onCloseRegisterDialog: func.isRequired,
  currentUser: object,
  loginDialogShow: bool.isRequired,
  registerDialogShow: bool.isRequired
};

const mapStateToProps = state => ({
  currentUser: state.common.currentUser,
  loginDialogShow: state.home.loginDialogShow,
  registerDialogShow: state.home.registerDialogShow
});

const mapDispatchToProps = dispatch => ({
  onClickLogout: () => dispatch({ type: LOGOUT }),
  onOpenLoginDialog: () => dispatch({ type: OPEN_LOGIN_DIALOG }),
  onCloseLoginDialog: () => dispatch({ type: CLOSE_LOGIN_DIALOG }),
  onOpenRegisterDialog: () => dispatch({ type: OPEN_REGISTER_DIALOG }),
  onCloseRegisterDialog: () => dispatch({ type: CLOSE_REGISTER_DIALOG })
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NavHeader);
