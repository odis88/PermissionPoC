package com.example.permissionpoc.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDoc {

    @Singular("organizations")
    private List<String> organizations;
    @Singular("retailers")
    private List<String> retailers;
}
