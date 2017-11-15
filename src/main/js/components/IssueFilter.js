import React, { Component } from 'react'
import PropTypes from 'prop-types'

export default class IssueFilter extends Component {
  render() {
    const { ballOwner } = this.props;
    return (
      <div>
        <label>Ball owner</label>
        <input type="text" value={ballOwner}/>
      </div>
    )
  }
}

IssueFilter.propTypes = {
  ballOwner: PropTypes.string.isRequired,
  status: PropTypes.string.isRequired
};
