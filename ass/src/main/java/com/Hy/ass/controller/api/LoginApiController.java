package com.Hy.ass.controller.api;

import com.Hy.ass.entity.User;
import com.Hy.ass.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginApiController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public Map<String, Object> login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role, // 接收前端传来的登录角色
            HttpSession session) {

        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findByUsername(username).orElse(null);

        // 校验：账号存在、密码正确、且数据库中的角色与登录选择的角色一致
        if (user != null && user.getPassword().equals(password) && user.getRole().equalsIgnoreCase(role)) {
            session.setAttribute("currentUser", user);
            result.put("success", true);
            result.put("nickname", user.getNickname());
            result.put("role", user.getRole());
            result.put("message", "登录成功");
        } else {
            result.put("success", false);
            result.put("message", "用户名、密码或登录角色错误");
        }
        return result;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate();
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "已退出");
        return result;
    }

    @GetMapping("/check")
    public Map<String, Object> checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        Map<String, Object> result = new HashMap<>();
        if (user != null) {
            result.put("loggedIn", true);
            result.put("nickname", user.getNickname());
            result.put("role", user.getRole());
        } else {
            result.put("loggedIn", false);
        }
        return result;
    }
}