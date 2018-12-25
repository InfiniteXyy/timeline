import React from 'react';
import { connect } from 'react-redux';

class Profile extends React.Component {
  render() {
    let { user } = this.props;
    return (
      <div>
        <div>username：{user.username}</div>
        <input />
        <div>image：..</div>
        <input />
        <div>email：{user.email}</div>
        <input />
        <div>
          <button>update</button>
        </div>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  user: state.common.currentUser
});

const mapDispatchToProps = dispatch => ({});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Profile);
