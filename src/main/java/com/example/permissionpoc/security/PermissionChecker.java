package com.example.permissionpoc.security;

import lombok.experimental.UtilityClass;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@UtilityClass
public class PermissionChecker {

    public static final String ROLES_ATTRIBUTE_NAME = "roles";
    public static final String PERMISSION_ATTRIBUTE_NAME = "permission";

    private final ExpressionParser parser = new SpelExpressionParser();

    public boolean checkPermission( Set<String> roles, PermissionDoc permissionDoc, Map<String, Object> methodArguments, String expression ) {
        Map<String, Object> arguments = new HashMap<>();

        arguments.put( ROLES_ATTRIBUTE_NAME, roles );
        arguments.put( PERMISSION_ATTRIBUTE_NAME, permissionDoc );
        arguments.putAll( methodArguments );

        StandardEvaluationContext context = new StandardEvaluationContext( arguments );
        context.addPropertyAccessor( new MapAccessor() );
        Expression exp = parser.parseExpression( expression );
        return Boolean.TRUE.equals( exp.getValue( context, Boolean.class ) );
    }

}
