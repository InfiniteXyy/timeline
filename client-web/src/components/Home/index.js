import React from 'react';
import { connect } from 'react-redux';
import { HOME_PAGE_LOADED, HOME_PAGE_UNLOADED, LOGIN } from '../../constants/actionTypes';
import agent from '../../agent';
import Button from 'react-bootstrap/lib/Button';
import InputGroup from 'react-bootstrap/lib/InputGroup';
import FormControl from 'react-bootstrap/lib/FormControl';

import './Home.css';

import Message from './Message';
import Row from 'react-bootstrap/es/Row';

const mapStateToProps = state => ({ ...state.home });
const mapDispatchToProps = dispatch => ({
  onLoad: payload => dispatch({ type: HOME_PAGE_LOADED, payload }),
  onUnload: () => dispatch({ type: HOME_PAGE_UNLOADED }),
  onLogin: (username, password) => dispatch({ type: LOGIN, payload: agent.Auth.login(username, password) })
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
      agent.Message.add(this.state.textField, new Date().getTime());
    };

    // 删
    this.handleDelete = id => () => {
      agent.Message.delete(id);
    };

    // 查
    this.refreshData = () => {
      this.props.onLoad(agent.Message.get());
    };
  }

  componentWillMount() {
    this.props.onLoad(agent.Message.get());
    this.props.onLogin('xyy', '1');
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
            <FormControl placeholder="有什么新鲜事？" aria-label="message-field" onChange={this.updateMessage} />
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

        {this.props.messages.map(i => (
          <Message item={i} onDelete={this.handleDelete} key={i.id} />
        ))}
      </div>
    );
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Home);
