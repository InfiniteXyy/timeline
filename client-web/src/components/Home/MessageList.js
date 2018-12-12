import React from 'react';
import Message from './Message';

const MessageList = props => {
  if (!props.messages) {
    return <h3>Loading...</h3>;
  }

  if (props.messages.length === 0) {
    return <h3>No articles are here... yet.</h3>;
  }

  return (
    <div>
      {props.messages.map(i => (
        <Message item={i} key={i.createdAt} />
      ))}
    </div>
  );
};

export default MessageList;
