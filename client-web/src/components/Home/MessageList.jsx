import React from 'react';
import { arrayOf} from 'prop-types';
import Message, { MessageProp } from './Message';

const MessageList = props => {
  const { messages } = props;
  if (!messages) {
    return null;
  }

  if (messages.length === 0) {
    return <div className="empty-list">空空如也</div>;
  }

  return (
    <div>
      {messages.map(i => (
        <Message item={i} key={i.id} />
      ))}
    </div>
  );
};

MessageList.propTypes = {
  messages: arrayOf(MessageProp).isRequired
};

export default MessageList;
