package com.truward.jdoblog.services.service;


import com.truward.jdoblog.services.domain.Account;
import com.truward.jdoblog.services.domain.BlogComment;
import com.truward.jdoblog.services.domain.BlogPost;
import com.truward.jdoblog.services.domain.Role;
import com.truward.jdoblog.services.traits.AccountDataId;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.support.JdoDaoSupport;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * All in one: Dao and Service, no interfaces (bad practice, don't do it in production)
 */
public class BlogService extends JdoDaoSupport {
    public Collection<Account> getAccounts() {
        return getJdoTemplate().find(Account.class, null, "created ascending");
    }

    public Account getAccountById(long accountId) {
        return getJdoTemplate().getObjectById(Account.class, accountId);
    }

    public Account getAccountByName(String displayName) {
        Collection<Account> accounts = getJdoTemplate().find(Account.class,
                "displayName == givenDisplayName", "String givenDisplayName", displayName);
        if (accounts.size() != 1) {
            throw new DataRetrievalFailureException("No or multiple accounts with the name given");
        }

        return accounts.iterator().next();
    }

    /**
     * Gets roles ordered by their names
     * @return Roles collection
     */
    public Collection<Role> getRoles() {
        return getJdoTemplate().find(Role.class, null, "name ascending");
    }

    public void updateAccountData(long accountId, final Map<AccountDataId, ?> accountData) {
        final Account account = getJdoTemplate().getObjectById(Account.class, accountId);

        getJdoTemplate().execute(new JdoCallback<Object>() {
            public Object doInJdo(PersistenceManager pm) throws JDOException {
                if (accountData.containsKey(AccountDataId.DISPLAY_NAME)) {
                    account.setDisplayName((String) accountData.get(AccountDataId.DISPLAY_NAME));
                }

                if (accountData.containsKey(AccountDataId.AVATAR_URL)) {
                    account.setAvatarUrl((String) accountData.get(AccountDataId.AVATAR_URL));
                }

                return null;
            }
        });
    }

    public Collection<BlogPost> getBlogPosts() {
        return getJdoTemplate().find(BlogPost.class, null, "created ascending");
    }

    public void saveAccount(String displayName, String avatarUrl) {
        final Account account = new Account();
        account.setDisplayName(displayName);
        account.setAvatarUrl(avatarUrl);
        account.setCreated(new Date());
        getJdoTemplate().makePersistent(account);
    }

    public void deleteAccount(long accountId) {
        final Account account = getAccountById(accountId);
        getJdoTemplate().deletePersistent(account);
    }

    public void saveBlogPost(long authorId, String title, String content) {
        final Account author = getJdoTemplate().getObjectById(Account.class, authorId);

        final BlogPost post = new BlogPost();
        post.setTitle(title);
        post.setContent(content);
        post.setCreated(new Date());
        post.setAuthor(author);
        post.setUpdated(null);
        author.getPosts().add(post);
        getJdoTemplate().makePersistent(post);
    }

    public void saveComment(long parentPostId, long authorId, Long parentCommentId, String content) {
        final BlogPost parentPost = getJdoTemplate().getObjectById(BlogPost.class, parentPostId);
        final Account author = getJdoTemplate().getObjectById(Account.class, authorId);

        final BlogComment comment = new BlogComment();
        comment.setAuthor(author);
        comment.setParentPost(parentPost);
        comment.setContent(content);
        comment.setCreated(new Date());
        getJdoTemplate().makePersistent(comment);

        if (parentCommentId != null) {
            // bind to the parent comment
            final BlogComment parentComment = getJdoTemplate().getObjectById(BlogComment.class, parentCommentId);
            getJdoTemplate().execute(new JdoCallback<Object>() {
                public Object doInJdo(PersistenceManager pm) throws JDOException {
                    parentComment.getChildComments().add(comment);
                    comment.setParentComment(parentComment);
                    return null;
                }
            });
        } else {
            // this comment is a direct reply to the post
            getJdoTemplate().execute(new JdoCallback<Object>() {
                public Object doInJdo(PersistenceManager pm) throws JDOException {
                    parentPost.getChildComments().add(comment);
                    return null;
                }
            });
        }
    }

    public BlogComment getComment(String content) {
        Collection<BlogComment> comments = getJdoTemplate().find(BlogComment.class,
                "content == givenComment", "String givenComment", content);

        if (comments.size() != 1) {
            throw new DataRetrievalFailureException("No such comment or more than one comment");
        }
        return comments.iterator().next();
    }

    /**
     * Gets the child comments that belongs to the given parent comment
     * @param parentComment Comment that is parent to the returned ones
     * @return Child comments
     */
    public Collection<BlogComment> getComments(BlogComment parentComment) {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("givenParentComment", parentComment);

        return getJdoTemplate().find(BlogComment.class,
                "parentComment == givenParentComment",
                "BlogComment givenParentComment",
                parameters,
                "created ascending");
    }

    public Collection<BlogComment> getComments(BlogPost parentPost) {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("givenParentPost", parentPost);

        return getJdoTemplate().find(BlogComment.class,
                "givenParentPost.childComments.contains(this)",
                "BlogPost givenParentPost",
                parameters,
                "created ascending");
    }

    public Collection<BlogComment> getComments() {
        return getJdoTemplate().find(BlogComment.class);
    }

    public void deleteComment(long commentId) {
        final BlogComment comment = getJdoTemplate().getObjectById(BlogComment.class, commentId);
        getJdoTemplate().deletePersistent(comment);
    }

    public Collection<Role> getAccountRoles(long accountId) {
        final Account account = getJdoTemplate().getObjectById(Account.class, accountId);
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("givenAccount", account);

        return getJdoTemplate().find(Role.class,
                "givenAccount.roles.contains(this)",
                "Account givenAccount",
                parameters,
                "name ascending");
    }

    public void saveRole(String name) {
        final Role role = new Role();
        role.setName(name);
        getJdoTemplate().makePersistent(role);
    }

    public void deleteRole(long roleId) {
        final Role role = getJdoTemplate().getObjectById(Role.class, roleId);
        getJdoTemplate().deletePersistent(role);
    }

    public void addAccountRole(long accountId, long roleId) {
        final Account account = getJdoTemplate().getObjectById(Account.class, accountId);
        final Role role = getJdoTemplate().getObjectById(Role.class, roleId);

        getJdoTemplate().execute(new JdoCallback<Object>() {
                public Object doInJdo(PersistenceManager pm) throws JDOException {
                    account.getRoles().add(role);
                    //role.getAssignees().add(account);
                    return null;
                }
            });
    }
}
