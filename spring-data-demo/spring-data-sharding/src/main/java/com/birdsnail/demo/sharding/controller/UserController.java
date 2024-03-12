package com.birdsnail.demo.sharding.controller;

import com.birdsnail.demo.sharding.entity.User;
import com.birdsnail.demo.sharding.entity.UserQueryBean;
import com.birdsnail.demo.sharding.service.IUserService;
import com.birdsnail.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * @param user user param
     * @return user
     */
    @PostMapping("add")
    public ResponseResult<User> add(@RequestBody User user) {
        log.info("add user param:{}", user);
        if (user.getId() == null) {
            userService.save(user);
        } else {
            userService.update(user);
        }
        return ResponseResult.success(userService.findById(user.getId()));
    }

    /**
     * @return user list
     */
    @GetMapping("edit/{userId}")
    public ResponseResult<User> edit(@PathVariable("userId") Long userId) {
        return ResponseResult.success(userService.findById(userId));
    }

    /**
     * @return user list 2
     */
    @GetMapping("edit2/{userId}")
    public ResponseResult<User> edit2(@PathVariable("userId") Long userId) {
        return ResponseResult.success(userService.findById2(userId));
    }

    /**
     * @return user list
     */
    @PostMapping("list")
    public ResponseResult<List<User>> list(@RequestBody UserQueryBean userQueryBean) {
        return ResponseResult.success(userService.findList(userQueryBean));
    }

    @PostMapping("delete")
    public ResponseResult<Integer> delete(@RequestParam Long userId) {
        return ResponseResult.success(userService.deleteById(userId));
    }
}
