

Domain Objects Reference:

1) Account - represents user account
2) Role - represents the user's role (Admin, Moderator, Reviewer, Writer, Reader, etc.).
   One user can have several roles.
3) BlogPost - post to the blog.
4) BlogComment - comment to the blog post organized in the tree structure.


Domain Objects Relations:

Account <-> Role (m:n)
Account <-> BlogPost (1:n)
Account <-> BlogComment (1:n)
BlogPost <-> BlogComment (1:n)
BlogComment -> BlogComment (1:n)     -- this relationship is introduced for having tree structure



