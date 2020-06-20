package org.teomant.appointment.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.teomant.appointment.security.domain.model.ActionNameEnum;
import org.teomant.appointment.security.domain.model.EntityNameEnum;
import org.teomant.appointment.user.domain.model.SiteUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class RightChecker {

    public boolean checkCanPerform(EntityNameEnum entity, ActionNameEnum action, SiteUser owner, SiteUser currentSiteUser) {

        boolean result = true;

        if (currentSiteUser == null
                || (owner != null && owner.equals(currentSiteUser) && !canOwn(currentSiteUser, entity, action) && !canAny(currentSiteUser, entity, action)
                || (owner != null && !owner.equals(currentSiteUser) && !canAny(currentSiteUser, entity, action)))
        ) {
            result = false;
        }

        return result;
    }

    private boolean canOwn(SiteUser currentSiteUser, EntityNameEnum entity, ActionNameEnum action) {
        boolean own = false;
        try {
            own = currentSiteUser.getRoles().stream()
                    .anyMatch(role -> role.getPrivileges().stream()
                            .anyMatch(privilege -> privilege.getAction().equals(modifyToOwn(action)) && privilege.getEntity().equals(entity)));
        } catch (Exception e) {
            log.info("no _OWN for " + action.name());
        }
        return canCommon(currentSiteUser, entity, action) || own;
    }

    private boolean canCommon(SiteUser currentSiteUser, EntityNameEnum entity, ActionNameEnum action) {
        return currentSiteUser.getRoles().stream()
                .anyMatch(role -> role.getPrivileges().stream()
                        .anyMatch(privilege -> privilege.getAction().equals(action) && privilege.getEntity().equals(entity)));
    }

    private ActionNameEnum modifyToOwn(ActionNameEnum action) {
        return ActionNameEnum.valueOf(action.name() + "_OWN");
    }

    private boolean canAny(SiteUser currentSiteUser, EntityNameEnum entity, ActionNameEnum action) {
        boolean any = false;
        try {
            any = currentSiteUser.getRoles().stream()
                    .anyMatch(role -> role.getPrivileges().stream()
                            .anyMatch(privilege -> privilege.getAction().equals(modifyToAny(action)) && privilege.getEntity().equals(entity)));
        } catch (Exception e) {
            log.info("no _ANY for " + action.name());
        }
        return canCommon(currentSiteUser, entity, action) || any;
    }

    private ActionNameEnum modifyToAny(ActionNameEnum action) {
        return ActionNameEnum.valueOf(action.name() + "_ANY");
    }
}
