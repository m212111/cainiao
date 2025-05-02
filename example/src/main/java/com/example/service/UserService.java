package com.example.service;

import com.example.entity.User;
import com.example.util.TokenUtil;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class UserService {
    private List<User> users = new CopyOnWriteArrayList<>();
    private Map<String, User> userMap = new HashMap<>();
    /**
     * 用户服务类，提供用户注册、登录、查询等功能。
     * @author wzy
     * @since 2025-05-01
     */
    @PostConstruct
    public void init() {
        users.add(new User("admin", "123456", "admin", "null"));
        users.add(new User("1", "1", "user", "SFM12323001"));
        users.add(new User("2", "2", "user", "SFM12323002"));
        users.forEach(u -> userMap.put(u.getUsername(), u));
        System.out.println("[用户初始化完成] 用户列表：");
        users.forEach(u -> System.out.println("- " + u.getUsername() + " / " + u.getRole()));
    }

    public boolean register(String username, String password, String identityCode) {
        if (getUserByUsername(username) != null || getUserByIdentityCode(identityCode) != null) {
            return false; // 用户名或身份码已存在
        }
        User newUser = new User(username, password, "user", identityCode);
        users.add(newUser);
        userMap.put(username, newUser);
        System.out.println("[注册成功] 用户：" + username);
        return true;
    }

    public String login(String username, String password) {
        User user = userMap.get(username);
        if (user != null && user.getPassword().equals(password)) {
            String token = TokenUtil.generateToken(username);
            System.out.println("[登录成功] 用户：" + username);
            return token;
        }
        return null;
    }

    public User getUserByUsername(String username) {
        return userMap.get(username);
    }

    public User getUserByIdentityCode(String identityCode) {
        for (User u : users) {
            if (u.getIdentityCode().equals(identityCode)) return u;
        }
        return null;
    }
    public String getIdentityCodeByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) { // 根据用户名匹配用户
                return u.getIdentityCode(); // 返回对应的身份码
            }
        }
        return null; // 如果未找到用户，返回 null
    }
    public List<User> getAllUsers() {
        return users;
    }

    public boolean deleteUser(String username) {
        User user = getUserByUsername(username);
        if (user != null) {
            if ("admin".equals(user.getRole())) {
                System.out.println("[删除用户失败] 管理员用户无法删除！");
                return false;
            }
            users.remove(user);
            userMap.remove(username);
            System.out.println("[删除用户成功] 用户：" + username);
            return true;
        }
        System.out.println("[删除用户失败] 用户：" + username + " 不存在！");
        return false;
    }


}