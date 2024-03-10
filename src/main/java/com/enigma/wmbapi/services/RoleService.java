package com.enigma.wmbapi.services;

import com.enigma.wmbapi.constant.UserRole;
import com.enigma.wmbapi.entity.Role;
import org.springframework.stereotype.Service;

public interface RoleService {
    Role getOrSave(UserRole role);
}
