package com.walletwise.main;

import com.walletwise.domain.adapters.ICryptoAdapter;
import com.walletwise.infra.persistence.entities.security.PrivilegeEntity;
import com.walletwise.infra.persistence.entities.security.RoleEntity;
import com.walletwise.infra.persistence.entities.security.UserEntity;
import com.walletwise.infra.persistence.repositories.security.IPrivilegeRepository;
import com.walletwise.infra.persistence.repositories.security.IRoleRepository;
import com.walletwise.infra.persistence.repositories.security.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class Setup implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    @Value("${spring.profiles.active}")
    private String profile;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IPrivilegeRepository privilegeRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ICryptoAdapter encoderAdapter;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;
        if (profile.equalsIgnoreCase("test")) return;

        PrivilegeEntity readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        PrivilegeEntity writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        PrivilegeEntity deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");

        List<PrivilegeEntity> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, deletePrivilege);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege, writePrivilege));
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);

        createAdminUserIfNotFound();

        alreadySetup = true;
    }

    PrivilegeEntity createPrivilegeIfNotFound(String name) {
        Optional<PrivilegeEntity> privilegeResult = privilegeRepository.findByNameAndActive(name, true);
        PrivilegeEntity privilege = null;
        if (privilegeResult.isEmpty()) {
            privilege = PrivilegeEntity.builder().name(name).active(true).build();
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    void createAdminUserIfNotFound() {
        if (userRepository.findByUsernameAndActive("admin", true).isEmpty()) {
            Optional<RoleEntity> adminRole = roleRepository.findByNameAndActive("ROLE_ADMIN", true);
            String password = encoderAdapter.encode("admin");
            UserEntity user = UserEntity
                    .builder()
                    .firstName("admin")
                    .lastName("admin")
                    .username("admin")
                    .email("gervasioarthur@gmail.com")
                    .roles(List.of(adminRole.get()))
                    .password(password)
                    .active(true)
                    .build();
            userRepository.save(user);
        }
    }

    RoleEntity createRoleIfNotFound(String name, Collection<PrivilegeEntity> privileges) {
        Optional<RoleEntity> roleResult = roleRepository.findByNameAndActive(name, true);
        RoleEntity role = null;
        if (roleResult.isEmpty()) {
            role = RoleEntity.builder().name(name).active(true).build();
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
