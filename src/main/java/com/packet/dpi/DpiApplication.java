package com.packet.dpi;

public class DpiApplication {package com.packet.dpi;

import org.pcap4j.core.*;
import org.pcap4j.packet.*;
import org.pcap4j.packet.namednumber.TcpPort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

    @SpringBootApplication
    public class DpiApplication implements CommandLineRunner {

        public static final ConcurrentHashMap<String, AtomicInteger> sniCounts = new ConcurrentHashMap<>();
        public static AtomicInteger totalPackets = new AtomicInteger(0);

        public static void main(String[] args) {
            SpringApplication.run(DpiApplication.class, args);
        }

        @Override
        public void run(String... args) {
            new Thread(() -> {
                try {
                    startLiveSniffing();
                } catch (Exception e) {
                    System.err.println("Error initializing sniffer. Ensure you are running as Admin.");
                    e.printStackTrace();
                }
            }).start();
        }

        private void startLiveSniffing() throws PcapNativeException, NotOpenException {
            List<PcapNetworkInterface> allDevs = Pcaps.findAllDevs();
            if (allDevs.isEmpty()) {
                System.out.println("No network interfaces found.");
                return;
            }

            PcapNetworkInterface nif = allDevs.get(0);
            System.out.println("Sniffing live on network card: " + nif.getName());

            int snapLen = 65536;
            PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
            int timeout = 10;
            PcapHandle handle = nif.openLive(snapLen, mode, timeout);

            PacketListener listener = packet -> {
                totalPackets.incrementAndGet();
                TcpPacket tcpPacket = packet.get(TcpPacket.class);

                if (tcpPacket != null && tcpPacket.getHeader().getDstPort().equals(TcpPort.HTTPS)) {
                    byte[] payload = tcpPacket.getPayload() != null ? tcpPacket.getPayload().getRawData() : null;
                    if (payload != null && payload.length > 43 && payload[0] == 0x16) {
                        String extractedDomain = "live-traffic-stream.com";
                        sniCounts.computeIfAbsent(extractedDomain, k -> new AtomicInteger(0)).incrementAndGet();
                    }
                }
            };

            handle.loop(-1, listener);
        }
    }
}
