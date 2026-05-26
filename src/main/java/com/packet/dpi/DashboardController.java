package com.packet.dpi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DashboardController {

    @GetMapping("/api/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPackets", DpiApplication.totalPackets.get());

        Map<String, Integer> domains = new HashMap<>();
        DpiApplication.sniCounts.forEach((k, v) -> domains.put(k, v.get()));

        stats.put("detectedDomains", domains);
        return stats;
    }
}