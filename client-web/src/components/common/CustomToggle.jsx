import React from 'react';
import { func, node } from 'prop-types';

export default class CustomToggle extends React.Component {
  constructor(props) {
    super(props);
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick(e) {
    e.preventDefault();
    const { onClick } = this.props;
    onClick(e);
  }

  render() {
    const { children } = this.props;
    return (
      <div role="button" onClick={this.handleClick} tabIndex={0} onKeyPress={this.handleClick}>
        {children}
      </div>
    );
  }
}

CustomToggle.propTypes = {
  onClick: func.isRequired,
  children: node.isRequired,
};
