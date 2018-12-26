import React from 'react';
import Message from './Message';

const MessageList = props => {
  if (!props.messages) {
    return null;
  }

  if (props.messages.length === 0) {
    return <div className="empty-list">空空如也</div>;
  }

  return (
    <div>
      {props.messages.map(i => (
        <Message item={i} key={i.id} />
      ))}
    </div>
  );
};

export default MessageList;
