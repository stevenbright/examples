package com.truward.jdoblog.util;

import com.truward.jdoblog.services.domain.Account;
import com.truward.jdoblog.services.domain.BlogComment;
import com.truward.jdoblog.services.domain.BlogPost;
import com.truward.jdoblog.services.traits.AccountDataId;
import org.junit.Assert;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Test utils
 */
public class Util {

    // Fake data
    private static class InitData {
        static String[] DISPLAY_NAMES = new String[] {
                "alex",
                "zen",
                "maria",
                "nick",
                "diana",
        };

        static String[] AVATAR_URLS = new String[] {
                "url3000-alex",
                "url1500-zen",
                "url2100-maria",
                "url6500-nick",
                "url1000-diana",
        };

        static String[] TITLES = new String[] {
                "t01",
                "t02",
                "t03",
                "t04",
                "t05",
        };

        static String[] CONTENT = new String[] {
                "Once upon a time",
                "This is another post",
                "And this is one another post",
                "As time goes by",
                "On and on",
        };
    }

    public static String getDisplayName(int index) {
        return InitData.DISPLAY_NAMES[index];
    }

    public static String getAvatarUrl(int index) {
        return InitData.AVATAR_URLS[index];
    }

    public static String getTitle(int index) {
        return InitData.TITLES[index];
    }

    public static String getContent(int index) {
        return InitData.CONTENT[index];
    }

    public static void assertAccountEquals(Account account, int index) {
        Assert.assertEquals(getDisplayName(index), account.getDisplayName());
        Assert.assertEquals(getAvatarUrl(index), account.getAvatarUrl());
    }

    public static void assertAccountsEquals(Collection<Account> accounts, int begin, int end) {
        Assert.assertEquals(end - begin, accounts.size());
        final Iterator<Account> it = accounts.iterator();
        for (int i = begin; i < end; ++i) {
            assertAccountEquals(it.next(), i);
        }
    }

    public static void assertPostEquals(BlogPost post, int index) {
        Assert.assertEquals(getTitle(index), post.getTitle());
        Assert.assertEquals(getContent(index), post.getContent());
    }

    public static void assertPostsEquals(Collection<BlogPost> posts, int begin, int end) {
        Assert.assertEquals(end - begin, posts.size());
        final Iterator<BlogPost> it = posts.iterator();
        for (int i = begin; i < end; ++i) {
            assertPostEquals(it.next(), i);
        }
    }

    public static Map<AccountDataId, ?> getAccountData(int index) {
        final Map<AccountDataId, Object> result = new HashMap<AccountDataId, Object>();
        result.put(AccountDataId.DISPLAY_NAME, getDisplayName(index));
        result.put(AccountDataId.AVATAR_URL, getAvatarUrl(index));
        return result;
    }

    public static <T> T at(Collection<T> accounts, int index) {
        Assert.assertTrue(index < accounts.size());
        final Iterator<T> it = accounts.iterator();
        for (; index > 0; --index) {
            it.next();
        }
        return it.next();
    }

    public static void assertCommentEquals(BlogComment comment, String content, long authorId, long postId) {
        Assert.assertEquals(content, comment.getContent());
        Assert.assertEquals(authorId, comment.getAuthor().getId());
        Assert.assertEquals(postId, comment.getParentPost().getId());
    }

    public static void assertCommentEquals(BlogComment comment, String content, long authorId, long postId,
                                           int childCommentsCount) {
        Assert.assertEquals(content, comment.getContent());
        Assert.assertEquals(authorId, comment.getAuthor().getId());
        Assert.assertEquals(postId, comment.getParentPost().getId());
        Assert.assertEquals(childCommentsCount, comment.getChildComments().size());
    }

    /**
     * Hidden ctor
     */
    private Util() {}
}
