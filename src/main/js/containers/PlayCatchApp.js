import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { diffChars } from 'diff'
import {
  changeValue,
  saveIssue,
  columnSort,
  fetchIssuesIfNeeded
} from '../actions/index'
import IssueFilter from '../components/IssueFilter'
import IssueGrid from '../components/IssueGrid'

class PlayCatchApp extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    const { dispatch } = this.props
    dispatch(fetchIssuesIfNeeded())
  }

  handleChange(issues, changes) {
    this.props.dispatch(changeValue(issues, changes))
  }

  handleColumnSort(issues, prop, order) {
    this.props.dispatch(columnSort(issues, prop, order))
  }

  handleSave(issues, changes) {
    const { dispatch } = this.props
    issues
      .filter(issue => changes.find(c => c.id === issue.id))
      .forEach(issue => dispatch(saveIssue(issue)))

  }

  render() {
    const { ballOwner, issues, changes, gridDefs } = this.props
    return (
      <div>
        <IssueGrid
          issues={issues}
          gridDefs={gridDefs}
          changes={changes}
          onChange={this.handleChange.bind(this)}
          onColumnSort={this.handleColumnSort.bind(this)}
          onSave={this.handleSave.bind(this)}/>
      </div>
    );
  }
}

PlayCatchApp.propTypes = {
  issues: PropTypes.array.isRequired,
  gridDefs: PropTypes.array.isRequired,
  changes: PropTypes.array.isRequired,
  isSaving: PropTypes.bool.isRequired,
  isFetching: PropTypes.bool.isRequired,
  dispatch: PropTypes.func.isRequired
}

function renderer(hot, TD, row, col, prop, value, cellProperties) {
  const changes = this
  if (changes.find(c => prop === c.prop && hot.getDataAtCell(row, 0) === c.id)) {
    TD.style.color = 'red'
  }
  TD.innerHTML = value
}

function textRenderer(hot, TD, row, col, prop, value, cellProperties) {
  const changes = this
  let change = changes.find(c => prop === c.prop && hot.getDataAtCell(row, 0) === c.id)
  if (change) {
    let fragment = document.createDocumentFragment();
    diffChars(change.oldValue || "", value || "").forEach(part => {
      let span = document.createElement('span')
      if (part.added) {
        span.style.color = 'red'
      } else if (part.removed) {
        span.style.textDecoration = 'line-through'
      }
      span.appendChild(document.createTextNode(part.value))
      fragment.appendChild(span)
    })
    TD.innerHTML = ""
    TD.appendChild(fragment)
  } else {
    TD.innerHTML = value
  }
}


function mapStateToProps(state) {
  const { issues, conditions } = state
  return {
    issues: issues.items,
    changes: issues.changes,
    isFetching: issues.isFetching,
    isSaving: issues.isSaving,
    gridDefs: [
      {
        data: 'id',
        readOnly: true
      },
      {
        data: 'subject',
        renderer: renderer.bind(issues.changes)
      },
      {
        data: 'description',
        renderer: renderer.bind(issues.changes)
      },
      {
        data: 'note',
        renderer: textRenderer.bind(issues.changes)
      },
      {
        data: 'ballOwner',
        editor: 'select',
        renderer: renderer.bind(issues.changes),
        selectOptions: ['our', 'their']
      },
      {
        data: 'status',
        editor: 'select',
        selectOptions: ['UNCON','CLOSE'],
        renderer: renderer.bind(issues.changes)
      },
      {
        data: 'createdBy',
        renderer: renderer.bind(issues.changes)
      },
      {
        data: 'createdAt',
        type: 'date',
        renderer: renderer.bind(issues.changes),
        dateFormat: 'YYYY-MM-DD',
        correctFormat: true,
        allowEmpty: true
      }
    ]
  }
}

export default connect(mapStateToProps)(PlayCatchApp)
