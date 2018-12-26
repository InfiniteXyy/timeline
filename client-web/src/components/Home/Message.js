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
        <div className="message-header">
          <div>
            <strong>{i.author.username}</strong>
            <span>@{i.author.username}</span>
          </div>
          <span className="message-date">{moment(i.createdAt).fromNow()}</span>
        </div>
        <p>{i.body}</p>
        {i.imageUrl ? <Image style={{ maxHeight: 220 }} src={i.imageUrl} thumbnail /> : null}
      </div>
    </div>
  );
};

export default Message;
