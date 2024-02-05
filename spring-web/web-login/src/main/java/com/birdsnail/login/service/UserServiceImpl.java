package com.birdsnail.login.service;

import com.birdsnail.login.dao.RoleResourceRepository;
import com.birdsnail.login.dao.UserRepository;
import com.birdsnail.login.dao.UserRoleRepository;
import com.birdsnail.login.dao.entity.ResourceEntity;
import com.birdsnail.login.dao.entity.RoleEntity;
import com.birdsnail.login.dao.entity.UserEntity;
import com.birdsnail.login.vo.UserInfoVO;
import com.birdsnail.login.vo.UserRegisterParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("找不到用户：" + username);
        }

        UserEntity userEt = user.get();
        List<RoleEntity> roles = userRoleRepository.findAllByUserId(userEt.getUserId());
        List<SimpleGrantedAuthority> roleGranted = roles.stream().map(r -> new SimpleGrantedAuthority(r.getId() + "")).toList();
        return User.builder()
                .username(username)
                .password(userEt.getPassword())
                .authorities(roleGranted)
                .build();
    }

    @Override
    public UserEntity registerUser(UserRegisterParam userRegisterParam) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegisterParam.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userRegisterParam.getPassword()));
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity findUserByName(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }

    @Override
    public UserInfoVO queryUser(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("找不到用户：" + username);
        }
        UserEntity userEntity = user.get();
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUserId(userEntity.getUserId());
        userInfoVO.setUsername(userEntity.getUsername());

        List<RoleEntity> roleEntityList = userRoleRepository.findAllByUserId(userEntity.getUserId());
        userInfoVO.setRoleList(roleEntityList);

        List<Long> roleIds = roleEntityList.stream().map(RoleEntity::getId).toList();
        if (!roleIds.isEmpty()) {
            List<ResourceEntity> resourceEntityList = roleResourceRepository.findAllByRoleId(roleIds);
            userInfoVO.setResourceList(resourceEntityList);
        }

        return userInfoVO;
    }

}
