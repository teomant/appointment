package org.teomant.appointment.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.teomant.appointment.security.domain.model.ActionNameEnum;
import org.teomant.appointment.security.domain.model.EntityNameEnum;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.user.domain.repository.UserRepository;
import org.teomant.appointment.user.persistance.model.UserEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class RightChecker {

    private final UserRepository userRepository;

    public boolean checkCanPerform(EntityNameEnum entity, ActionNameEnum action, User owner) {

        boolean result = true;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = null;
        if (principal instanceof UserEntity) {
            currentUser = userRepository.findByUsername(((UserEntity) principal).getUsername());
        }

        if (currentUser == null
                || (owner != null && owner.equals(currentUser) && !canOwn(currentUser, entity, action) && !canAny(currentUser, entity, action)
                || (owner != null && !owner.equals(currentUser) && !canAny(currentUser, entity, action)))
        ) {
            result = false;
        }

        return result;
    }

    private boolean canOwn(User currentUser, EntityNameEnum entity, ActionNameEnum action) {
        boolean own = false;
        try {
            own = currentUser.getRoles().stream()
                    .anyMatch(role -> role.getPrivileges().stream()
                            .anyMatch(privilege -> privilege.getAction().equals(modifyToOwn(action)) && privilege.getEntity().equals(entity)));
        } catch (Exception e) {
            log.info("no _OWN for " + action.name());
        }
        return canCommon(currentUser, entity, action) || own;
    }

    private boolean canCommon(User currentUser, EntityNameEnum entity, ActionNameEnum action) {
        return currentUser.getRoles().stream()
                .anyMatch(role -> role.getPrivileges().stream()
                        .anyMatch(privilege -> privilege.getAction().equals(action) && privilege.getEntity().equals(entity)));
    }

    private ActionNameEnum modifyToOwn(ActionNameEnum action) {
        return ActionNameEnum.valueOf(action.name() + "_OWN");
    }

    private boolean canAny(User currentUser, EntityNameEnum entity, ActionNameEnum action) {
        boolean any = false;
        try {
            any = currentUser.getRoles().stream()
                    .anyMatch(role -> role.getPrivileges().stream()
                            .anyMatch(privilege -> privilege.getAction().equals(modifyToAny(action)) && privilege.getEntity().equals(entity)));
        } catch (Exception e) {
            log.info("no _ANY for " + action.name());
        }
        return canCommon(currentUser, entity, action) || any;
    }

    private ActionNameEnum modifyToAny(ActionNameEnum action) {
        return ActionNameEnum.valueOf(action.name() + "_ANY");
    }
}
