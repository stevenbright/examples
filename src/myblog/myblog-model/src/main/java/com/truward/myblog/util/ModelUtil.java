package com.truward.myblog.util;

import com.truward.myblog.model.user.Profile;
import com.truward.myblog.model.user.Role;

import java.util.List;


public final class ModelUtil {

    public static Profile createProfile(String login, String email) {
        final Profile profile = new Profile();
        profile.setLogin(login);
        profile.setEmail(email);
        return profile;
    }

    public static Role createRole(String name) {
        final Role role = new Role();
        role.setName(name);
        return role;
    }

    public static void writeRole(StringBuilder builder, Role role) {
        builder.append("{id: ").append(role.getId())
                .append(", name: '").append(role.getName())
                .append("'}");
    }

    public static String toString(Profile profile) {
        final StringBuilder builder = new StringBuilder();
        
        builder.append("{id: ");
        builder.append(profile.getId());
        builder.append(", login: '");
        builder.append(profile.getLogin());
        builder.append("', email: '");
        builder.append(profile.getEmail());
        builder.append("'");

        if (profile.getRoles() != null && profile.getRoles().size() > 0) {
            builder.append(", roles: [");
            boolean next = false;
            for (final Role role : profile.getRoles()) {
                if (next) {
                    builder.append(", ");
                } else {
                    next = true;
                }
                writeRole(builder, role);
            }
            builder.append("]");
        }

        builder.append("}");

        return builder.toString();
    }

    public static String toString(List<Profile> profiles) {
        final StringBuilder builder = new StringBuilder();

        builder.append("[");
        boolean next = false;

        for (final Profile profile : profiles) {
            if (next) {
                builder.append(", ");
            } else {
                next = true;
            }
            builder.append(toString(profile));
        }

        builder.append("]");

        return builder.toString();
    }

    private ModelUtil() {}
}
