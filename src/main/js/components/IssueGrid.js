import React, { Component } from 'react'
import PropTypes from 'prop-types'
import HotTable from 'react-handsontable'
import {
  saveIssue
} from '../actions/index'


export default class IssueGrid extends Component {
  static defaultProps() {
    return  {
      width: 800,
      height: 600
    }
  }

  render() {
    const {
      onChange, onSave, onColumnSort,
      issues, gridDefs, changes
    } = this.props

    return (
      <div>
        <nav className="navbar navbar-light bg-light">
          <ul className="navbar-nav mr-auto">
            <li className="nav-item">
              <button className="btn" type="button" onClick={() => onSave(issues, changes)}><i className="fa fa-floppy-o" aria-hidden="true"></i>Save</button>
            </li>
          </ul>
        </nav>

      <HotTable root="hot" settings={{
          data: issues,
          dataSchema: {
            'id': null,
            'subject': null,
            'description': null,
            'note': null,
            'ballOwner': null,
            'status': null,
            'createdAt': null,
            'createdBy': null
          },
          columns: gridDefs,
          width: this.props.width,
          height: this.props.height,
          colHeaders: gridDefs.map(d => d.data),
          rowHeaders: true,
          minSpareRows: 1,
          columnSorting: true,
          onAfterChange: (c, source) => {
            if (source !== 'loadData') {
              onChange(issues, {
                row: c[0][0],
                col: gridDefs.findIndex(d => d.data === c[0][1]),
                prop: c[0][1],
                oldValue: c[0][2],
                newValue: c[0][3]
              })
            }
          },
          onBeforeColumnSort: (col, order) => {
            let prop = gridDefs[col].data
            onColumnSort(issues, prop, order)
            return false
          }
      }}/>
      </div>
    )
  }
}

IssueGrid.propTypes = {
  issues: PropTypes.array.isRequired,
  changes: PropTypes.array.isRequired,
  width: PropTypes.number,
  height: PropTypes.number,
  onChange: PropTypes.func.isRequired,
  onSave: PropTypes.func.isRequired
}
