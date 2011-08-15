package com.truward.rsw.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooEntity
public class Profile {

    @NotNull
    @Size(min = 3, max = 32)
    private String displayName;

    @NotNull
    @Size(min = 3, max = 32)
    private String login;

    @NotNull
    private Integer age;
}
