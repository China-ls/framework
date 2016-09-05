package com.infinite.water.entity;

import java.util.List;

public class SystemInformation {
    public String id;
    public String state;
    public long state_since;
    public String version;
    public String home_location;
    public String base_location;
    public JvmMetrics jvm_metrics;
    public long current_time;
    public int connection_counter;
    public int connected;
    public int messages_sent;
    public int messages_received;
    public int read_counter;
    public int write_counter;
    public List<String> virtual_hosts;
    public List<String> connectors;

    public static class JvmMetrics {
        public HeapMemory heap_memory;
        public HeapMemory non_heap_memory;
        public int classes_loaded;
        public int classes_unloaded;
        public int threads_current;
        public int threads_peak;
        public String os_arch;
        public String os_name;
        public long os_memory_total;
        public int os_memory_free;
        public int os_swap_total;
        public long os_swap_free;
        public int os_fd_open;
        public int os_fd_max;
        public double os_load_average;
        public long os_cpu_time;
        public int os_processors;
        public String runtime_name;
        public String jvm_name;
        public int uptime;
        public long start_time;

        public static class HeapMemory {
            public int used;
            public int alloc;
            public int max;
        }
    }
}
