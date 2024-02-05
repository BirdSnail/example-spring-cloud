package com.birdsnail.login;

import com.birdsnail.login.dao.ResourceRepository;
import com.birdsnail.login.dao.RoleRepository;
import com.birdsnail.login.dao.RoleResourceRepository;
import com.birdsnail.login.dao.UserRoleRepository;
import com.birdsnail.login.dao.entity.*;
import com.birdsnail.login.service.UserService;
import com.birdsnail.login.util.JsonUtil;
import com.birdsnail.login.vo.RoleInitList;
import com.birdsnail.login.vo.UserInitList;
import com.birdsnail.login.vo.UserRegisterParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
@EnableConfigurationProperties(value = {UserInitList.class, RoleInitList.class})
public class SpringSecurityApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInitList userInitList;

    @Autowired
    private RoleInitList roleInitList;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private RoleResourceRepository roleResourceRepository;


    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("user list:{}", JsonUtil.toJsonStr(userInitList));
        log.info("role list:{}", JsonUtil.toJsonStr(roleInitList));

        // 保存角色
        List<RoleEntity> roleEts = roleInitList.getRoleList().stream()
                .distinct().map(r -> new RoleEntity().setRole(r.getRole())).toList();
        roleRepository.saveAllAndFlush(roleEts);
        Map<String, RoleEntity> roleMap = roleEts.stream().collect(Collectors.toMap(RoleEntity::getRole, Function.identity()));

        // 保存权限
        List<ResourceEntity> resourceEntityList = roleInitList.getRoleList().stream()
                .map(RoleInitList.Role::getResource).distinct().map(resource -> new ResourceEntity().setPath(resource)).toList();
        resourceRepository.saveAllAndFlush(resourceEntityList);
        Map<String, ResourceEntity> resourceMap = resourceEntityList.stream().collect(Collectors.toMap(ResourceEntity::getPath, Function.identity()));

        saveRoleResource(roleInitList.getRoleList(), roleMap, resourceMap);

        for (UserInitList.User user : userInitList.getUser()) {
            UserRegisterParam registerParam = new UserRegisterParam();
            registerParam.setUsername(user.getName());
            registerParam.setPassword(user.getPassword());
            UserEntity userEntity = userService.registerUser(registerParam);
            log.info("账号创建成功：user={}", userEntity);

            // 保存用户角色信息
            saveUserRole(userEntity.getUserId(), user.getRoles(), roleMap);
        }
    }

    private void saveUserRole(Long userId, List<String> roles, Map<String, RoleEntity> roleMap) {
        List<UserRoleEntity> userRoleEntityList = new ArrayList<>();
        for (String role : roles) {
            RoleEntity roleEntity = roleMap.get(role);
            if (roleEntity != null) {
                UserRoleEntity userRoleEt = new UserRoleEntity();
                userRoleEt.setUserId(userId);
                userRoleEt.setRoleId(roleEntity.getId());
                userRoleEntityList.add(userRoleEt);
            }
        }
        if (!userRoleEntityList.isEmpty()) {
            userRoleRepository.saveAll(userRoleEntityList);
        }
    }

    private void saveRoleResource(List<RoleInitList.Role> roleList, Map<String, RoleEntity> roleMap, Map<String, ResourceEntity> resourceMap) {
        List<RoleResourceEntity> rrEtList = new ArrayList<>();

        for (RoleInitList.Role role : roleList) {
            RoleEntity roleEntity = roleMap.get(role.getRole());
            ResourceEntity resourceEt = resourceMap.get(role.getResource());
            if (resourceEt != null && roleEntity != null) {
                RoleResourceEntity rr = new RoleResourceEntity();
                rr.setRoleId(roleEntity.getId());
                rr.setResourceId(resourceEt.getId());
                rrEtList.add(rr);
            }
        }
        if (!rrEtList.isEmpty()) {
            roleResourceRepository.saveAll(rrEtList);
        }
    }
}
