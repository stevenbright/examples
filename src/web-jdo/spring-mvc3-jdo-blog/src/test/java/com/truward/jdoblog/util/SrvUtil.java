package com.truward.jdoblog.util;

import com.truward.jdoblog.services.domain.Account;
import com.truward.jdoblog.services.service.BlogService;

import java.util.Collection;


public class SrvUtil {
    public static void saveAccount(BlogService blogService, int index) {
        blogService.saveAccount(Util.getDisplayName(index), Util.getAvatarUrl(index));
    }

    public static void saveAccounts(BlogService blogService, int count) {
        for (int index = 0; index < count; ++index) {
            blogService.saveAccount(Util.getDisplayName(index), Util.getAvatarUrl(index));
        }
    }

    public static void saveBlogPost(BlogService blogService, Collection<Account> accounts, int accountIndex, int postIndex) {
        blogService.saveBlogPost(Util.at(accounts, accountIndex).getId(),
                Util.getTitle(postIndex), Util.getContent(postIndex));
    }


    /**
     * Hidden ctor
     */
    private SrvUtil() {}
}
