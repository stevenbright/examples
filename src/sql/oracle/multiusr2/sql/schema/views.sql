
---
--- user view
---

CREATE VIEW $schema.user_view AS
  SELECT
    user_id,
    ac.email,
    ac.created,
    p.display_name,
    p.avatar_url
  FROM
    $schema.user_account ac
    JOIN $schema.user_profile p USING (user_id)
;

