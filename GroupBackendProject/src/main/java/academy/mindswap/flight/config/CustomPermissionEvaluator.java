package academy.mindswap.flight.config;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class CustomPermissionEvaluator implements PermissionEvaluator {


    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || targetDomainObject == null || permission == null) {
            return false;
        }
        String targetType = targetDomainObject.getClass().getName();
        return hasPermission(authentication, targetDomainObject.hashCode(), targetType, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

}
