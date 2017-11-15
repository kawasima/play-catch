SELECT I.*, J.note, J.created_at AS updated_at, J.created_by AS updated_by
FROM issues I
LEFT JOIN journals J
  ON I.issue_id = J.issue_id
  AND J.created_at = (
  SELECT MAX(JA.created_at)
  FROM journals JA
  WHERE JA.issue_id = J.issue_id
  GROUP BY JA.issue_id)

