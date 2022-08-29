package com.example.permissionpoc;

import com.example.permissionpoc.security.Permission;
import com.example.permissionpoc.security.PermissionChecker;
import com.example.permissionpoc.security.PermissionDoc;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

@UtilityClass
public class PermissionUtil {

    boolean checkPermission( Set<String> roles, PermissionDoc permissionDoc, Class<?> clazz, Map<String, Object> methodArguments, String methodName) {
        Method[] methods = clazz.getMethods();
        String expression = null;
        for( Method method : methods ) {
            if (method.getName().equals( methodName )) {
                expression = method.getAnnotation( Permission.class ).expression();
            }
        }

        assert expression != null;

        return PermissionChecker.checkPermission( roles, permissionDoc, methodArguments, expression );
    }

}
