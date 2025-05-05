package com.example.service;

import com.example.entity.PackageInfo;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.*;

/**
 * 提供与包裹相关的服务，包括添加包裹、查询包裹、更新状态和删除包裹等功能。
 * 该服务管理所有包裹信息，包括根据接收者用户名、包裹 ID 等查询包裹，并支持取件和包裹状态更新。
 *
 * @author 马子杰
 * @version 1.0
 * @since 2025-04-25
 */
@Service
public class PackageService {
    private List<PackageInfo> packages = new ArrayList<>();

    @PostConstruct
    public void init() {
        packages.add(new PackageInfo("JDX123456790", "京东快递", "1-1-4154", "admin", "1", "运城职业技术大学"));
        packages.add(new PackageInfo("6935941203408", "顺丰快递", "1-9-1981", "admin", "1", "运城学院"));
        System.out.println("[快递初始化完成]");
    }

    public void addPackage(PackageInfo pkg) {
        packages.add(pkg);
        System.out.println("[寄件成功] " + pkg.getSender() + " -> " + pkg.getReceiver() + " 快递：" + pkg.getId());
    }

    public List<PackageInfo> getByReceiver(String username) {
        List<PackageInfo> result = new ArrayList<>();
        for (PackageInfo p : packages) {
            if (p.getReceiver().equals(username)) result.add(p);
        }
        return result;
    }

    public boolean pickup(String username, String id, String code) {
        for (PackageInfo p : packages) {
            if (p.getId().equals(id) && p.getReceiver().equals(username) && !p.isPickedUp()) {
                if (p.getPickupCode().equals(code)) {
                    p.setPickedUp(true);
                    System.out.println("[取件成功] " + username + " 取走了包裹：" + id);
                    return true;
                }
            }
        }
        return false;
    }



    /**
     * 管理员实现
     * 提供快递的查询、状态更新与删除等功能
     *
     * @author wzy
     * @since 2025-05-01
     */
    public List<PackageInfo> getAllPackages() {
        return new ArrayList<>(packages);
    }

    // 更新快递的取件状态
    public boolean updatePackageStatus(String id, boolean isPickedUp) {
        Optional<PackageInfo> optionalPackage = packages.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (optionalPackage.isPresent()) {
            optionalPackage.get().setPickedUp(isPickedUp);
            System.out.println("[更新状态成功] 快递：" + id + " 取件状态：" + (isPickedUp ? "已取件" : "未取件"));
            return true;
        }

        return false;
    }

    // 删除快递
    public boolean deletePackage(String id) {
        Optional<PackageInfo> optionalPackage = packages.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (optionalPackage.isPresent()) {
            packages.remove(optionalPackage.get());
            System.out.println("[删除快递成功] 快递：" + id);
            return true;
        }

        System.out.println("[删除快递失败] 快递：" + id + " 不存在");
        return false;
    }

    // 根据 ID 获取快递信息
    public PackageInfo getById(String id) {
        return packages.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
