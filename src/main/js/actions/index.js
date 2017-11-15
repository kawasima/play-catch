import fetch from 'isomorphic-fetch'

let credMeta = document.querySelector("meta[name='bouncr:credential']")
let headerBase = Object.assign({'accept': 'application/json'},
  credMeta ? {'X-Bouncr-Credential': credMeta.content} : {}
)


export const SORT_COLUMN = 'SORT_COLUMN';
export function columnSort(issues, prop, order) {
  return {
    type: SORT_COLUMN,
    issues: [...issues].sort((a, b) => {
      return order ? a[prop] > b[prop] : a[prop] < b[prop]
    })
  }
}

export const CHANGE_VALUE = 'CHANGE_VALUE';
export function changeValue(issues, changes) {
  return {
    type: CHANGE_VALUE,
    id: issues[changes.row].id,
    prop: changes.prop,
    newValue: changes.newValue,
    oldValue: changes.oldValue
  }
}

export const REQUEST_SAVE_ISSUE = 'REQUEST_SAVE_ISSUE';
export function requestSaveIssue() {
  return {
    type: REQUEST_SAVE_ISSUE
  }
}

export const RECEIVE_SAVE_ISSUE = 'RECEIVE_SAVE_ISSUE';
export function receiveSaveIssue() {
  return {
    type: RECEIVE_SAVE_ISSUE
  }
}

function createSaveParameters(issue) {
  if (issue.id) {
    return {
      method: 'PUT',
      url: '/issue/' + issue.id,
      body: JSON.stringify(issue)
    }
  } else {
    return {
      method: 'POST',
      url: '/issues',
      body: JSON.stringify((({subject, description, ballOwner, status, createdAt, createdBy}) =>
        ({subject, description, ballOwner, status, createdAt, createdBy}))(issue))
    }
  }
}

export function saveIssue(issue) {
  return function(dispatch) {
    dispatch(requestSaveIssue())


    let { method, url, body } = createSaveParameters(issue)
    return fetch(url, {
        method: method,
        headers: Object.assign(headerBase, {
          'content-type': 'application/json'
        }),
        body: body
      })
      .then(response => response.json())
      .then(json => dispatch(receiveSaveIssue(json)))
  }
}

export const REQUEST_ISSUES = 'REQUEST_ISSUES';
export function requestIssues() {
  return {
    type: REQUEST_ISSUES
  };
}

export const RECEIVE_ISSUES = 'RECEIVE_ISSUES';
export function receiveIssues(json) {
  return {
    type: RECEIVE_ISSUES,
    issues: json
  }
}

export function fetchIssues() {
  return function(dispatch) {
    dispatch(requestIssues());

    return fetch('/issues', {
        method: 'GET',
        headers:Object.assign(headerBase, {})
      })
      .then(response => response.json())
      .then(json => dispatch(receiveIssues(json)));
  };
}

function shouldFetchIssues(state) {
  const issues = state.issues
  if (!issues) {
    return true
  } else if (issues.isFetching) {
    return false
  } else {
    return true
  }
}

export function fetchIssuesIfNeeded() {
  return (dispatch, getState) => {
    if (shouldFetchIssues(getState())) {
      return dispatch(fetchIssues())
    }
  }
}
