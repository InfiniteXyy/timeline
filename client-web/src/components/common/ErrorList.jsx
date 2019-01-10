import React from 'react';
import { Alert } from 'react-bootstrap';
import { arrayOf, object } from 'prop-types';

const ErrorList = props => {
  const { errors } = props;
  if (errors == null) {
    return null;
  }
  return (
    <ul className="errors-list">
      {Object.keys(errors).map(key => (
        <Alert key={key} variant="danger">
          <b>{key}</b>
          {errors[key]}
        </Alert>
      ))}
    </ul>
  );
};

ErrorList.propTypes = {
  errors: arrayOf(object).isRequired,
};

export default ErrorList;
