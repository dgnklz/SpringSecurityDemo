package com.dgnklz.springsecurity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole{
    ADMIN("admin"),
    MODERATOR("mod");

    private final String userRoleName;

    /*UserRole(String userRoleName) {
        this.userRoleName = userRoleName;
    }*/

    static public boolean isMember(String roleName) {
        UserRole[] roles = UserRole.values();
        for (UserRole role : roles)
            if(role.userRoleName.equals(roleName))
                return true;
        return false;
    }

}
