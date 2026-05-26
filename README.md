# Deep Packet Inspection (DPI) Engine

An enterprise-grade, real-time network traffic analyzer built with Java and Spring Boot. This engine interfaces directly with native network hardware to intercept, parse, and analyze raw network packets in real-time, displaying the metrics on a live web dashboard.

## 🚀 Features

* **Live Packet Sniffing:** Hooks directly into Wi-Fi/Ethernet adapters to capture raw data streams.
* **Protocol Parsing:** Specifically identifies and tracks TLS/HTTPS handshakes.
* **Real-Time Dashboard:** A local Spring Boot web server hosting an HTML dashboard that tracks network metrics dynamically.
* **Hardware Integration:** Utilizes JNA (Java Native Access) to bridge Java with native C++ Windows packet capture drivers (Npcap/WinPcap).

## 🛠️ Technology Stack

* **Backend:** Java 17, Spring Boot 3
* **Networking:** Pcap4J, Npcap (Windows)
* **Frontend:** HTML5, CSS3
* **Build Tool:** Maven

## ⚙️ Setup & Installation

### 1. Prerequisites
Because Java cannot natively read raw hardware network streams, this application requires a C-level packet capture driver to be installed on your host machine.
* Download and install **[Npcap](https://npcap.com/#download)**.
* **Crucial:** During installation, you MUST check the box that says `"Install Npcap in WinPcap API-compatible Mode"`.

### 2. Configure Your Network Card
Windows hosts multiple invisible network adapters (VPNs, Bluetooth, etc.). You must point the application to your active Wi-Fi or Ethernet card.
1. Run the application once to print the list of available network interfaces to the terminal.
2. Identify the index number of your primary active adapter (e.g., `Intel Wi-Fi 6` or `Realtek PCIe GbE`).
3. Update the `activeNetworkCardIndex` variable in `DpiApplication.java` with your specific index number.

### 3. Build and Run
Clone the repository and build the project using Maven:
```bash
mvn clean install