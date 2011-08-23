
---
--- user view
---

CREATE VIEW $schema.user_view AS
  SELECT
    u_acc.user_id,
    u_acc.email,
    u_acc.created,
    u_prof.display_name,
    u_prof.avatar_url
  FROM
    $schema.user_account u_acc
    JOIN $schema.user_profile u_prof USING (user_id)
;

