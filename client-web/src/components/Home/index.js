import React from 'react';
import { connect } from 'react-redux';
import { ADD_MESSAGE, HOME_PAGE_LOADED, HOME_PAGE_UNLOADED, LOAD_MORE } from '../../constants/actionTypes';
import agent from '../../agent';
import InputGroup from 'react-bootstrap/lib/InputGroup';
import FormControl from 'react-bootstrap/lib/FormControl';

import './Home.css';

import Row from 'react-bootstrap/es/Row';
import MessageList from './MessageList';

const mapStateToProps = state => ({ ...state.messageList, currentUser: state.common.currentUser });
const mapDispatchToProps = dispatch => ({
  onLoad: payload => dispatch({ type: HOME_PAGE_LOADED, payload }),
  onUnload: () => dispatch({ type: HOME_PAGE_UNLOADED }),
  onAddMessage: payload => dispatch({ type: ADD_MESSAGE, payload }),
  onLoadMore: payload => dispatch({ type: LOAD_MORE, payload })
});

class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      messageBody: '',
      imageOpen: false,
      imageUrl: ''
    };

    this.updateBody = ev => {
      const state = this.state;
      const newState = Object.assign({}, state, { messageBody: ev.target.value });
      this.setState(newState);
    };

    this.handleSubmit = () => {
      if (this.props.currentUser === null) {
        alert('请先登录');
        return;
      }
      if (this.state.messageBody === '') {
        alert('输入不能为空');
        return;
      }
      this.props.onAddMessage(agent.Message.add(this.state.messageBody));
      this.setState({
        messageBody: ''
      });
    };
    this.handleOpenImage = () => {
      this.setState(({ imageOpen }) => ({ imageOpen: !imageOpen }));
    };

    this.handleClickUpload = () => {
      this.imageInput.click();
    };

    this.handleImageChosen = e => {
      console.log(e.target.files[0]);
    };

    this.refreshData = () => {
      this.props.onLoad(agent.Message.get());
    };

    this.loadMore = () => {
      const lastMessage = this.props.messages[this.props.messages.length - 1];
      this.props.onLoadMore(agent.Message.loadMore(lastMessage.createdAt));
    };
  }

  componentWillMount() {
    this.refreshData();
  }

  componentWillUnmount() {
    this.props.onUnload();
  }

  render() {
    let { inProgress } = this.props;
    return (
      <div className="main-list-container">
        <div className="message-input-container">
          <Row className="justify-content-center">
            <InputGroup>
              <FormControl placeholder="有什么想说的？" onChange={this.updateBody} value={this.state.messageBody} />
              <i className="fa fa-picture-o btn-circle" onClick={this.handleOpenImage} />
              <i className="fa fa-paper-plane-o btn-circle" onClick={this.handleSubmit} />
            </InputGroup>
          </Row>
          {this.state.imageOpen ? (
            <Row>
              <div className="image-upload" onClick={this.handleClickUpload}>
                <input
                  ref={ip => {
                    this.imageInput = ip;
                  }}
                  style={{ display: 'none' }}
                  type="file"
                  accept="image/*"
                  onChange={this.handleImageChosen}
                />
                +
              </div>
            </Row>
          ) : null}
        </div>

        <MessageList messages={this.props.messages} />
        <Row
          className="justify-content-center align-items-center load-more-button"
          onClick={this.loadMore}
          disabled={inProgress}
        >
          {inProgress ? '加载中...' : '加载更多'}
        </Row>
      </div>
    );
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Home);
