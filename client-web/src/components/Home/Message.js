import React from 'react';
import moment from 'moment';
import Image from 'react-bootstrap/lib/Image';
import { defaultAvatar } from '../App';
/** @namespace i.createdAt */
/** @namespace i.author */
const Message = props => {
  let i = props.item;
  return (
    <div className="message-container">
      <Image src={i.author.image === '' ? defaultAvatar : i.author.image} roundedCircle />
      <div className="right-container">
        <span>
          <strong>{i.author.username}</strong> · {moment(i.createdAt).fromNow()}
        </span>
        <p>{i.body}</p>
      </div>
    </div>
  );
};

export default Message;
