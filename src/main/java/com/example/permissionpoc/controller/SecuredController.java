package com.example.permissionpoc.controller;

import com.example.permissionpoc.security.Permission;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SecuredController {

    @Permission(expression = "( roles.contains('ROLE_USER') || roles.contains('ROLE_ADMIN') ) && permission.organizations.contains( organizationId )")
    @GetMapping
    public String getOrganizationInfo( @RequestParam String organizationId ) {
        return "Organization: " + organizationId;
    }

    @Permission(expression = "roles.contains('ROLE_ADMIN') && permission.organizations.contains( organizationId )")
    @PutMapping
    public String updateOrganization( @RequestParam String organizationId ) {
        return "Organization updated: " + organizationId;
    }

    @Permission(expression = "( roles.contains('ROLE_USER') || roles.contains('ROLE_ADMIN') ) && permission.retailers.contains( filter.retailerId )")
    @PostMapping
    public String getStores( @RequestBody Filter filter ) {
        return "Stores for retailer: " + filter.getRetailerId();
    }

    @ExceptionHandler
    ResponseEntity<String> handleNotFoundException( SecurityException e ) {
        return ResponseEntity.status( HttpStatus.FORBIDDEN )
            .body( e.getMessage() );
    }

}
