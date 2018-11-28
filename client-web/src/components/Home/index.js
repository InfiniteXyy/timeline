import React from 'react';
import { connect } from 'react-redux';
import { ADD_MESSAGE, HOME_PAGE_LOADED, HOME_PAGE_UNLOADED } from '../../constants/actionTypes';
import agent from '../../agent';
import Button from 'react-bootstrap/lib/Button';
import InputGroup from 'react-bootstrap/lib/InputGroup';
import FormControl from 'react-bootstrap/lib/FormControl';

import './Home.css';

import Row from 'react-bootstrap/es/Row';
import MessageList from './MessageList';

const mapStateToProps = state => ({ ...state.messageList, currentUser: state.common.currentUser });
const mapDispatchToProps = dispatch => ({
  onLoad: payload => dispatch({ type: HOME_PAGE_LOADED, payload }),
  onUnload: () => dispatch({ type: HOME_PAGE_UNLOADED }),
  onAddMessage: payload => dispatch({ type: ADD_MESSAGE, payload })
});

class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = { textField: '' };

    this.updateMessage = ev => {
      const state = this.state;
      const newState = Object.assign({}, state, { textField: ev.target.value });
      this.setState(newState);
    };

    // 增
    this.handleSubmit = () => {
      if (this.props.currentUser === null) {
        alert('请先登录');
        return;
      }
      this.props.onAddMessage(agent.Message.add(this.state.textField, new Date().getTime()));
      this.setState({
        textField: ''
      });
    };

    // 查
    this.refreshData = () => {
      this.props.onLoad(agent.Message.get());
    };
  }

  componentWillMount() {
    this.props.onLoad(agent.Message.get());
  }

  componentWillUnmount() {
    this.props.onUnload();
  }

  render() {
    let { inProgress } = this.props;
    return (
      <div>
        <Row className="message-input-container justify-content-center">
          <InputGroup>
            <FormControl
              placeholder="有什么新鲜事？"
              aria-label="message-field"
              onChange={this.updateMessage}
              value={this.state.textField}
            />
            <InputGroup.Append>
              <Button variant="light" className="btn btn-outline-secondary submit-button" onClick={this.handleSubmit}>
                发送
              </Button>
            </InputGroup.Append>
          </InputGroup>
        </Row>
        <Row
          className="justify-content-center align-items-center refresh-button"
          onClick={this.refreshData}
          disabled={inProgress}
        >
          {inProgress ? 'loading...' : '查看更多新闻'}
        </Row>

        <MessageList messages={this.props.messages} />
      </div>
    );
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Home);
