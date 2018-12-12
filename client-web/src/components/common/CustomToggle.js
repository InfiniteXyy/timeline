import React from 'react';

export default class CustomToggle extends React.Component {
  constructor(props) {
    super(props);
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick(e) {
    e.preventDefault();
    this.props.onClick(e);
  }

  render() {
    return <div onClick={this.handleClick}>{this.props.children}</div>;
  }
}