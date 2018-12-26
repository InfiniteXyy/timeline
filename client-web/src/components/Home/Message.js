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
        {Number(i.id) % 3 === 0 ? (
          <Image
            src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvx-NjD0rTsqjOJL0pUPRrcscFGVfA87hNob5-wAK4cOTg62Wx"
            thumbnail
          />
        ) : null}
      </div>
    </div>
  );
};

export default Message;
