package com.krista.extend.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * SnowFlake算法 64位Long类型生成唯一ID<br/>
 * 第一位0，表明正数<br/>
 * 2-42，41位，表示毫秒时间戳差值，起始值自定义<br/>
 * 43-52，10位，机器编号，5位数据中心编号，5位进程编号<br/>
 * 53-64，12位，毫秒内计数器 本机内存生成，性能高<br/>
 *
 * 主要就是三部分： 时间戳，进程id，序列号<br/>
 * 时间戳41位<br/>
 * 进程id 10位<br/>
 * 序列号12位<br/>
 *
 * @author PC
 * @since JDK 1.6
 */
public class IdGenerator {
    /**
     * 起始时间戳，毫秒级别13位，2^41 刚好是13位数字
     */
    private final static long BEGIN_TS = 1483200000000L;

    /**
     * 上一次时间戳
     */
    private static long lastTs = 0L;

    /**
     * 进程id
     */
    private static long processId;

    /**
     * 进程id位数，2^10 = 1024
     */
    private static int processIdBits = 10;

    /**
     * 序列号
     */
    private static long sequence = 0L;
    /**
     * 序列号位数，2^12 = 4096
     */
    private static int sequenceBits = 12;

    public IdGenerator() {
        IdGenerator.processId = getProcessID() % 1024;
    }

    public static synchronized long nextId() {
        long ts = System.currentTimeMillis();

        // 刚刚生成的时间戳比上次的时间戳还小，出错
        if (ts < lastTs) {
            throw new RuntimeException("时间戳顺序错误");
        }

        // 刚刚生成的时间戳跟上次的时间戳一样，则需要生成一个sequence序列号
        if (ts == lastTs) {
            // sequence循环自增
            sequence = (sequence + 1) & ((1 << sequenceBits) - 1);

            // 如果sequence=0则需要重新生成时间戳
            if (sequence == 0) {
                // 且必须保证时间戳序列往后
                ts = nextTs(lastTs);
            }
        } else {
            // 如果ts>lastTs，时间戳序列已经不同了，此时可以不必生成sequence了，直接取0
            sequence = 0L;
        }
        // 更新lastTs时间戳
        lastTs = ts;
        return ((ts - BEGIN_TS) << (processIdBits + sequenceBits))
                | (processId << sequenceBits) | sequence;
    }

    public static String randomId(){
        return nextId() + "";
    }

    protected static long nextTs(long lastTs) {
        long ts = System.currentTimeMillis();
        while (ts <= lastTs) {
            ts = System.currentTimeMillis();
        }
        return ts;
    }

    public static final int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0])
                .intValue();
    }
}