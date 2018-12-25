import React from 'react';
import { Alert } from 'react-bootstrap';

class ErrorList extends React.Component {
  render() {
    let errors = this.props.errors;
    if (errors == null) {
      return null;
    } else {
      return (
        <ul className={'errors-list'}>
          {Object.keys(errors).map(key => (
            <Alert key={key} variant={'danger'}>
              <b>{key}: </b>
              {errors[key]}
            </Alert>
          ))}
        </ul>
      );
    }
  }
}

export default ErrorList;
