package com.example.permissionpoc.security;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Aspect
@Component
@RequiredArgsConstructor
@Profile( "!test" )
public class PermissionAspect {

    public static final String ROLES_ATTRIBUTE_NAME = "roles";
    public static final String PERMISSION_ATTRIBUTE_NAME = "permission";
    private final PermissionRepo permissionRepo;

    @Before( "@annotation(permission)" )
    public void checkPermissions( JoinPoint joinPoint, Permission permission ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ( User ) auth.getPrincipal();
        Set<String> roles = user.getAuthorities().stream().map( GrantedAuthority::getAuthority ).collect( Collectors.toSet() );

        MethodSignature signature = ( MethodSignature ) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        Map<String, Object> context = new HashMap<>();
        for( int i = 0; i < parameterNames.length; i++ ) {
            context.put( parameterNames[i], args[i] );
        }

        boolean allowed = PermissionChecker.checkPermission( roles, permissionRepo.getUserPermission( user.getUsername() ), context, permission.expression() );

        if( !allowed ) {
            throw new SecurityException( "Forbidden" );
        }
    }

}
