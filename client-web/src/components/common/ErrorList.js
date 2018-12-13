import React from 'react';

class ErrorList extends React.Component {
  render() {
    let errors = this.props.errors;
    if (errors === null) {
      return null;
    }
    return (
      <ul className={"errors-list"}>
        {Object.keys(errors).map(key => (
          <li key={key}>
            {key} {errors[key]}
          </li>
        ))}
      </ul>
    );
  }
}

export default ErrorList;
