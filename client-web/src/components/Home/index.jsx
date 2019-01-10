import React from 'react';
import { connect } from 'react-redux';
import InputGroup from 'react-bootstrap/lib/InputGroup';
import FormControl from 'react-bootstrap/lib/FormControl';
import { arrayOf, bool, func, object } from 'prop-types';

import lrz from 'lrz';

import './Home.css';

import Row from 'react-bootstrap/es/Row';

import agent from '../../agent';
import {
  ADD_MESSAGE,
  HOME_PAGE_LOADED,
  HOME_PAGE_UNLOADED,
  LOAD_MORE
} from '../../constants/actionTypes';
import MessageList from './MessageList';
import { MessageProp } from './Message';

class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      messageBody: '',
      imageOpen: false,
      imageUrl: ''
    };

    this.updateBody = ev => {
      const newState = Object.assign({}, this.state, { messageBody: ev.target.value });
      this.setState(newState);
    };

    this.handleSubmit = () => {
      const { currentUser, onAddMessage } = this.props;
      const { messageBody, imageUrl } = this.state;
      if (currentUser === null) {
        alert('请先登录');
        return;
      }
      if (messageBody === '') {
        alert('内容不能为空');
        return;
      }
      onAddMessage(agent.Message.add(messageBody, imageUrl));
      this.setState({
        messageBody: '',
        imageUrl: ''
      });
    };
    this.handleOpenImage = () => {
      this.setState(({ imageOpen }) => ({ imageOpen: !imageOpen }));
    };

    this.handleClickUpload = () => {
      this.imageInput.click();
    };

    this.handleImageChosen = e => {
      const image = e.target.files[0];
      lrz(image).then(rst => {
        const formData = new FormData();
        let blob = rst.file;
        if (typeof blob.name !== 'string') {
          blob = new File([blob], 'foo.jpg', {
            type: blob.type
          });
        }
        formData.append('file', blob);
        agent.Upload.image(formData).then(res => {
          this.setState({ imageUrl: `http://timeline.infinitex.cn/img${res.data.path}` });
        });
      });
    };

    this.clearChosenImage = () => {
      this.setState({ imageUrl: '' });
    };
    this.refreshData = () => {
      const { onLoad } = this.props;
      onLoad(agent.Message.get());
    };

    this.loadMore = () => {
      const { messages, onLoadMore } = this.props;
      const lastMessage = messages[messages.length - 1];
      onLoadMore(agent.Message.loadMore(lastMessage.createdAt));
    };
  }

  componentWillMount() {
    this.refreshData();
  }

  componentWillUnmount() {
    const { onUnload } = this.props;
    onUnload();
  }

  render() {
    const { inProgress, messages } = this.props;
    const { imageUrl, messageBody, imageOpen } = this.state;
    let imageContainer = (
      <div
        className="image-upload"
        onClick={this.handleClickUpload}
        role="button"
        tabIndex={0}
        onKeyPress={this.handleClickUpload}
      >
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
    );
    if (imageUrl !== '') {
      imageContainer = (
        <div className="preview-container">
          <div className="preview-image-container">
            <img className="preview-image" src={imageUrl} alt="preview" />
          </div>
          <div className="btn-close">
            <i
              className="fa fa-times"
              onClick={this.clearChosenImage}
              role="button"
              tabIndex={0}
              onKeyPress={this.clearChosenImage}
            />
          </div>
        </div>
      );
    }
    return (
      <div className="main-list-container">
        <div className="message-input-container">
          <Row className="justify-content-center">
            <InputGroup>
              <FormControl
                placeholder="有什么想说的？"
                onChange={this.updateBody}
                value={messageBody}
              />
              <i
                className="fa fa-picture-o btn-circle"
                style={imageOpen ? { color: '#1da1f2' } : {}}
                onClick={this.handleOpenImage}
                role="button"
                onKeyPress={this.handleOpenImage}
                tabIndex={0}
              />
              <i
                className="fa fa-paper-plane-o btn-circle"
                onClick={this.handleSubmit}
                role="button"
                onKeyPress={this.handleSubmit}
                tabIndex={0}
              />
            </InputGroup>
          </Row>
          {imageOpen ? <Row>{imageContainer}</Row> : null}
        </div>

        <MessageList messages={messages} />
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
Home.propTypes = {
  inProgress: bool.isRequired,
  messages: arrayOf(MessageProp).isRequired,
  currentUser: object,
  onAddMessage: func.isRequired,
  onLoadMore: func.isRequired,
  onLoad: func.isRequired,
  onUnload: func.isRequired
};
const mapStateToProps = state => ({ ...state.messageList, currentUser: state.common.currentUser });
const mapDispatchToProps = dispatch => ({
  onLoad: payload => dispatch({ type: HOME_PAGE_LOADED, payload }),
  onUnload: () => dispatch({ type: HOME_PAGE_UNLOADED }),
  onAddMessage: payload => dispatch({ type: ADD_MESSAGE, payload }),
  onLoadMore: payload => dispatch({ type: LOAD_MORE, payload })
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Home);
