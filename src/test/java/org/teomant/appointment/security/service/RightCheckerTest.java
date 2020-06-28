package org.teomant.appointment.security.service;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.teomant.appointment.security.domain.model.ActionNameEnum;
import org.teomant.appointment.security.domain.model.EntityNameEnum;
import org.teomant.appointment.security.domain.model.Privilege;
import org.teomant.appointment.security.domain.model.Role;
import org.teomant.appointment.user.domain.model.SiteUser;

import java.util.Collections;

class RightCheckerTest {

    @Test
    public void ownRightsTest() {
        RightChecker rightChecker = new RightChecker();
        SiteUser owner = getOwner();
        SiteUser current = getOwner();
        EntityNameEnum entity = EntityNameEnum.APPOINTMENT;
        ActionNameEnum action = ActionNameEnum.MODIFY;

        Assert.isTrue(rightChecker.checkCanPerform(entity, action, owner, current), "User can change his own");
    }

    @Test
    public void otherRightsTest() {
        RightChecker rightChecker = new RightChecker();
        SiteUser owner = getOwner();
        SiteUser current = getOther();
        EntityNameEnum entity = EntityNameEnum.APPOINTMENT;
        ActionNameEnum action = ActionNameEnum.MODIFY;

        Assert.isTrue(!rightChecker.checkCanPerform(entity, action, owner, current), "User can`t change other`s");
    }

    @Test
    public void anyRightsTest() {
        RightChecker rightChecker = new RightChecker();
        SiteUser owner = getOwner();
        SiteUser current = getAdmin();
        EntityNameEnum entity = EntityNameEnum.APPOINTMENT;
        ActionNameEnum action = ActionNameEnum.MODIFY;

        Assert.isTrue(rightChecker.checkCanPerform(entity, action, owner, current), "Admin can change other`s");
        Assert.isTrue(rightChecker.checkCanPerform(entity, action, current, current), "Admin can change own");
    }

    @Test
    public void commonRightsTest() {
        RightChecker rightChecker = new RightChecker();
        SiteUser owner = getOwner();
        SiteUser current = getCommon();
        EntityNameEnum entity = EntityNameEnum.APPOINTMENT;
        ActionNameEnum action = ActionNameEnum.MODIFY;

        Assert.isTrue(rightChecker.checkCanPerform(entity, action, owner, current), "Common can change other`s");
        Assert.isTrue(rightChecker.checkCanPerform(entity, action, current, current), "Common can change own");
    }

    private SiteUser getOwner() {
        SiteUser siteUser = new SiteUser();
        siteUser.setId(1L);
        siteUser.setRoles(Collections.singleton(getOwnRole()));
        return siteUser;
    }

    private SiteUser getOther() {
        SiteUser siteUser = new SiteUser();
        siteUser.setId(2L);
        siteUser.setRoles(Collections.singleton(getOwnRole()));
        return siteUser;
    }

    private SiteUser getAdmin() {
        SiteUser siteUser = new SiteUser();
        siteUser.setId(3L);
        siteUser.setRoles(Collections.singleton(getAdminRole()));
        return siteUser;
    }

    private SiteUser getCommon() {
        SiteUser siteUser = new SiteUser();
        siteUser.setId(4L);
        siteUser.setRoles(Collections.singleton(getCommonRole()));
        return siteUser;
    }

    private Role getOwnRole() {
        Role role = new Role();
        Privilege privilege = new Privilege();
        privilege.setEntity(EntityNameEnum.APPOINTMENT);
        privilege.setAction(ActionNameEnum.MODIFY_OWN);
        role.setPrivileges(Collections.singleton(privilege));
        return role;
    }

    private Role getAdminRole() {
        Role role = new Role();
        Privilege privilege = new Privilege();
        privilege.setEntity(EntityNameEnum.APPOINTMENT);
        privilege.setAction(ActionNameEnum.MODIFY_ANY);
        role.setPrivileges(Collections.singleton(privilege));
        return role;
    }

    private Role getCommonRole() {
        Role role = new Role();
        Privilege privilege = new Privilege();
        privilege.setEntity(EntityNameEnum.APPOINTMENT);
        privilege.setAction(ActionNameEnum.MODIFY);
        role.setPrivileges(Collections.singleton(privilege));
        return role;
    }

}