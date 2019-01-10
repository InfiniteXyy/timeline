import React from 'react';
import moment from 'moment';
import Image from 'react-bootstrap/lib/Image';
import { shape, string } from 'prop-types';
import defaultValue from '../../constants/defaultValue';

const Message = props => {
  const { item } = props;
  return (
    <div className="message-container">
      <Image src={item.author.image === '' ? defaultValue.avatar : item.author.image} roundedCircle />
      <div className="right-container">
        <div className="message-header">
          <div>
            <strong>{item.author.username}</strong>
            <span>{`@${item.author.username}`}</span>
          </div>
          <span className="message-date">{moment(item.createdAt).fromNow()}</span>
        </div>
        <p>{item.body}</p>
        {item.imageUrl ? <Image style={{ maxHeight: 220 }} src={item.imageUrl} thumbnail /> : null}
      </div>
    </div>
  );
};

export const MessageProp = shape({
  author: shape({
    username: string,
    image: string
  }),
  createdAt: string,
  imageUrl: string,
  body: string
});

Message.propTypes = {
  item: MessageProp.isRequired
};
export default Message;
