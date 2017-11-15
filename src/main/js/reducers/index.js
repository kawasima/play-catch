import { combineReducers } from 'redux'
import {
  REQUEST_ISSUES,
  RECEIVE_ISSUES,
  REQUEST_SAVE_ISSUE,
  RECEIVE_SAVE_ISSUE,
  CHANGE_VALUE,
  SORT_COLUMN
} from '../actions/index'

function conditions(
  state = {
    ballOwner: null,
    status: null
  },
  action) {
  return state;
}

function issues(
  state = {
    items: [],
    changes: [],
    isFetching: false,
    isSaving: false
  },
  action
) {
  switch(action.type) {
    case REQUEST_ISSUES:
      return Object.assign({}, state, {
        isFetching: true
      })
    case RECEIVE_ISSUES:
      return Object.assign({}, state, {
        items: action.issues,
        isFetching: false
      })
    case REQUEST_SAVE_ISSUE:
      return Object.assign({}, state, {
        isSaving: true
      })
    case RECEIVE_SAVE_ISSUE:
      return Object.assign({}, state, {
        isSaving: false,
        changes: []
      })
    case CHANGE_VALUE:
      if (action.oldValue === action.newValue) {
        return state
      } else {
        let idx = state.changes.findIndex(c => action.id === c.id && action.prop === c.prop)
        return Object.assign({}, state, {
          changes: [...state.changes.slice(0, idx < 0 ? state.changes.length : idx),
            {
              id: action.id,
              prop: action.prop,
              oldValue: idx < 0 ? action.oldValue : state.changes[idx].oldValue,
              newValue: action.newValue
            },...state.changes.slice(idx < 0 ? state.changes.length : idx + 1)
          ]
        })
      }
    case SORT_COLUMN:
      return Object.assign({}, state, {
        items: action.issues
      })
    default:
      return state
  }
}

const rootReducers = combineReducers({
  issues,
  conditions
})
export default rootReducers
