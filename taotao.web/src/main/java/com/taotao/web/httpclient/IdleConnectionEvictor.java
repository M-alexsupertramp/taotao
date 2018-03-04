package com.taotao.web.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;
/**
 * 定时清理无效链接的线程
 */
public class IdleConnectionEvictor extends Thread {

    private final HttpClientConnectionManager connMgr;

    private volatile boolean shutdown;

    public IdleConnectionEvictor(HttpClientConnectionManager connMgr) {
        this.connMgr = connMgr;
        // 启动线程
        this.start();
    }

    // 定义线程任务，定时关闭失效的连接
    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    // 每隔5秒钟一次
                    wait(5000);
                    // 关闭失效的连接
                    connMgr.closeExpiredConnections();
                }
            }
        } catch (InterruptedException ex) {
            // 结束
        }
        System.out.println("清理任务 即将结束。。");
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}

