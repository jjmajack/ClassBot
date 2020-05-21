package com.peuyanaga.classbot.Model;

/**
 * Created by DELL on 2019-12-21.
 */

public class Server {
    private String name;
    private String description;
    private String ipAddress;
    private String status;
    private int port;

    public Server(){}
    public Server(String name, String description, String ipAddress, String status, int port) {
        this.name = name;
        this.description = description;
        this.ipAddress = ipAddress;
        this.status = status;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
