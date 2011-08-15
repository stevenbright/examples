package com.truward.blogboard.domain;

/**
 * Represents user account data with the additional "isFriend" field
 */
public final class UserAccount extends UserInfo {
    private boolean friend;

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }
}
