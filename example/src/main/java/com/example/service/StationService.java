package com.example.service;

import com.example.entity.Station;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
/**
 * 站点创建
 *
 *
 * @author 田治源
 * @version 1.0
 * @since 2025-04-25
 */
@Service
public class StationService {
    private List<Station> stations = new ArrayList<>();

    @PostConstruct
    public void init() {
        stations.add(new Station("a"));
        stations.add(new Station("运城职业技术大学"));
        stations.add(new Station("运城学院"));
        System.out.println("[驿站初始化完成]");
    }

    public List<Station> getAll() {
        return stations;
    }

    public boolean exists(String name) {
        return stations.stream().anyMatch(s -> s.getName().equals(name));
    }
}

