package com.example.Controller;

import com.example.entity.PackageInfo;
import com.example.entity.User;
import com.example.service.PackageService;
import com.example.service.StationService;
import com.example.service.UserService;
import com.example.util.TokenUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 * CainiaoController 控制器
 * 包含控制台与 Web 端接口逻辑
 *
 * @author wzy & 张雨聪
 * @since 2025-04-26
 * - 张雨聪：控制台交互逻辑实现
 * - wzy： Web API 接口开发
 */

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CainiaoController {

    @Autowired
    private UserService userService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private StationService stationService;

    private static String currentToken = null;

    // 控制台交互逻辑
    @PostConstruct
    public void initConsole() {
        new Thread(this::runConsole).start();
    }

    private void runConsole() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n请选择操作：");
            System.out.println("1. 登录");
            System.out.println("2. 注册");
            System.out.println("3. 查看我的快递");
            System.out.println("4. 寄快递");
            System.out.println("5. 取快递");
            System.out.println("6. 退出");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> login(scanner);
                case 2 -> register(scanner);
                case 3 -> viewMyPackages();
                case 4 -> sendPackage(scanner);
                case 5 -> pickupPackage(scanner);
                case 6 -> {
                    System.out.println("退出系统");
                    return;
                }
                default -> System.out.println("无效选项，请重新输入！");
            }
        }
    }

    private void login(Scanner scanner) {
        System.out.println("请输入用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();

        currentToken = userService.login(username, password);
        if (currentToken != null) {
            System.out.println("登录成功，欢迎 " + username);
        } else {
            System.out.println("登录失败，用户名或密码错误！");
        }
    }

    private void register(Scanner scanner) {
        System.out.println("请输入用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();
        System.out.println("请输入身份码");
        String identityCode = scanner.nextLine();
        boolean success = userService.register(username, password,identityCode);
        if (success) {
            System.out.println("注册成功！");
        } else {
            System.out.println("用户名已存在！");
        }
    }

    private void viewMyPackages() {
        if (currentToken == null) {
            System.out.println("请先登录！");
            return;
        }

        String username = TokenUtil.getUsername(currentToken);
        List<PackageInfo> packages = packageService.getByReceiver(username);
        if (packages.isEmpty()) {
            System.out.println("您没有收到任何快递！");
        } else {
            System.out.println("您的快递：");
            packages.forEach(p ->
                    System.out.println("- 快递ID: " + p.getId() + " / 状态: " + (p.isPickedUp() ? "已取件" : "未取件")));
        }
    }

    private void sendPackage(Scanner scanner) {
        if (currentToken == null) {
            System.out.println("请先登录！");
            return;
        }

        String sender = TokenUtil.getUsername(currentToken);
        System.out.println("请输入快递ID：");
        String id = scanner.nextLine();
        System.out.println("请输入快递公司：");
        String courier = scanner.nextLine();
        System.out.println("请输入取件码：");
        String pickupCode = scanner.nextLine();
        System.out.println("请输入收件人用户名：");
        String receiver = scanner.nextLine();
        System.out.println("请输入驿站名称：");
        String station = scanner.nextLine();

        if (!stationService.exists(station)) {
            System.out.println("无效的驿站名称！");
            return;
        }

        PackageInfo packageInfo = new PackageInfo(id, courier, pickupCode, sender, receiver, station);
        packageService.addPackage(packageInfo);
        System.out.println("寄件成功！");
    }

    private void pickupPackage(Scanner scanner) {
        if (currentToken == null) {
            System.out.println("请先登录！");
            return;
        }

        String username = TokenUtil.getUsername(currentToken);
        System.out.println("请输入快递ID：");
        String id = scanner.nextLine();
        System.out.println("请输入取件码：");
        String code = scanner.nextLine();

        boolean success = packageService.pickup(username, id, code);
        if (success) {
            System.out.println("取件成功！");
        } else {
            System.out.println("取件失败，检查快递ID和取件码是否正确！");
        }
    }

    // ---------------- Web 接口 ----------------

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        String token = userService.login(user.getUsername(), user.getPassword());

        Map<String, String> result = new HashMap<>();
        if (token != null) {
            result.put("type", "success");
            result.put("message", "登录成功");
            result.put("token", token); // 把 token 返回给前端
        } else {
            result.put("type", "fail");
            result.put("message", "用户名或密码错误");
        }
        return result;
    }


    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        boolean success = userService.register(user.getUsername(), user.getPassword(),user.getIdentityCode());

        Map<String, String> result = new HashMap<>();
        if (success) {
            result.put("type", "success");
            result.put("message", "注册成功");
        } else {
            result.put("type", "fail");
            result.put("message", "用户名已存在");
        }
        return result;
    }

    @GetMapping("/packages")
    public List<PackageInfo> getPackages(@RequestParam String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        return packageService.getByReceiver(username);  // 包括取件码和状态
    }


    @PostMapping("/send")
    public Map<String, String> send(@RequestBody PackageInfo packageInfo,
                                    @RequestHeader("Authorization") String authHeader) {
        Map<String, String> result = new HashMap<>();

        // 检查 Token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            result.put("type", "fail");
            result.put("message", "未提供有效的Token");
            return result;
        }

        String token = authHeader.substring(7);
        String sender;
        try {
            sender = TokenUtil.getUsername(token);
        } catch (Exception e) {
            result.put("type", "fail");
            result.put("message", "Token无效");
            return result;
        }

        // 检查驿站
        if (!stationService.exists(packageInfo.getStation())) {
            result.put("type", "fail");
            result.put("message", "无效的驿站名称");
            return result;
        }

        // 设置寄件人
        packageInfo.setSender(sender);
        packageService.addPackage(packageInfo);

        result.put("type", "success");
        result.put("message", "寄件成功");
        return result;
    }

    // 用于接收 JSON 请求中的取件信息
    public static class PickupRequest {
        private String username;
        private String id;
        private String pickupCode;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getPickupCode() { return pickupCode; }
        public void setPickupCode(String pickupCode) { this.pickupCode = pickupCode; }
    }

    @PostMapping("/pickup")
    public Map<String, String> pickup(@RequestBody PickupRequest request) {
        Map<String, String> result = new HashMap<>();
        boolean success = packageService.pickup(request.getUsername(), request.getId(), request.getPickupCode());

        if (success) {
            result.put("type", "success");
            result.put("message", "取件成功");
        } else {
            result.put("type", "fail");
            result.put("message", "已经取走了");
        }
        return result;
    }


    // 管理员查看所有用户
    @GetMapping("/admin/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 管理员删除用户
    @DeleteMapping("/admin/user/{username}")
    public Map<String, String> deleteUser(@PathVariable String username) {
        boolean success = userService.deleteUser(username);
        Map<String, String> result = new HashMap<>();
        if (success) {
            result.put("type", "success");
            result.put("message", "用户删除成功");
        } else {
            result.put("type", "fail");
            result.put("message", "用户删除失败");
        }
        return result;
    }
    // 获取所有快递
    @GetMapping("/admin/packages")
    public List<PackageInfo> getPackages() {
        return packageService.getAllPackages();
    }
    // 更新快递的取件状态
    @PatchMapping("/admin/package/{id}/status")
    public Map<String, String> updatePackageStatus(@PathVariable String id, @RequestBody Map<String, Boolean> status) {
        boolean updated = packageService.updatePackageStatus(id, status.get("isPickedUp"));
        Map<String, String> result = new HashMap<>();
        if (updated) {
            result.put("type", "success");
            result.put("message", "更新成功");
        } else {
            result.put("type", "fail");
            result.put("message", "更新失败，找不到该快递");
        }
        return result;
    }

    // 删除快递
    @DeleteMapping("/admin/package/{id}")
    public Map<String, String> deletePackage(@PathVariable String id) {
        boolean deleted = packageService.deletePackage(id);
        Map<String, String> result = new HashMap<>();
        if (deleted) {
            result.put("type", "success");
            result.put("message", "删除成功");
        } else {
            result.put("type", "fail");
            result.put("message", "删除失败，找不到该快递");
        }
        return result;
    }
    @PostMapping("/scan-pickup")
    public Map<String, String> scanPickup(@RequestBody Map<String, String> body) {
        String identityCode = body.get("identityCode");
        String packageId = body.get("packageId");

        Map<String, String> result = new HashMap<>();

        // 验证参数是否完整
        if (identityCode == null || packageId == null) {
            result.put("type", "fail");
            result.put("message", "参数不完整");
            return result;
        }

        // 验证身份码
        User user = userService.getUserByIdentityCode(identityCode);
        if (user == null) {
            result.put("type", "fail");
            result.put("message", "无效身份码");
            return result;
        }

        // 验证快递是否属于该用户
        PackageInfo packageInfo = packageService.getById(packageId);
        if (packageInfo == null) {
            result.put("type", "fail");
            result.put("message", "快递不存在");
        } else if (!packageInfo.getReceiver().equals(user.getUsername())) {
            result.put("type", "fail");
            result.put("message", "请放置专用身份码");
        } else if (packageInfo.isPickedUp()) {
            result.put("type", "fail");
            result.put("message", "快递已被取件");
        } else {
            packageInfo.setPickedUp(true);
            result.put("type", "success");
            result.put("message", "出库成功");
        }

        return result;
    }

    @GetMapping("/getSfmByToken")
    public Map<String, Object> getSfmByToken(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> result = new HashMap<>();

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            result.put("type", "fail");
            result.put("message", "未提供有效的Token");
            return result;
        }

        String token = authHeader.substring(7); // 去掉 "Bearer "
        String username;
        try {
            username = TokenUtil.getUsername(token);
        } catch (Exception e) {
            result.put("type", "fail");
            result.put("message", "Token解析失败");
            return result;
        }

        String sfm = userService.getIdentityCodeByUsername(username);
        if (sfm == null) {
            result.put("type", "fail");
            result.put("message", "找不到身份码");
        } else {
            result.put("type", "success");
            result.put("identityCode", sfm);
        }

        return result;
    }


}


