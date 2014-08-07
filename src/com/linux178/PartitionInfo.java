package com.linux178;


public class PartitionInfo {
    private String ip = null;
    private String partition = null;
    private String total = null;
    private String used = null;
    private String free = null;
    private String used_percent = null;

    public PartitionInfo(String partition, String total,
                         String used,String free,
                         String used_percent) {
        this.partition = partition;
        this.free = free;
        this.used = used;
        this.total = total;
        this.used_percent = used_percent;
    }

    public String getPartition() {
        return partition;
    }
    public String getIp() {
        return ip;
    }
    public String getTotal() {
        return total;
    }
    public String getUsed() {
        return used;
    }
    public String getFree() {
        return free;
    }
    public String getUsed_percent() {
        return used_percent;
    }
}
