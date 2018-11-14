package com.yanwu.demo.saga.server.dao.model;

import java.io.Serializable;

public class DemoServer implements Serializable {
    /**
     * <pre>
     * 
     * 表字段 : demo_server.server_id
     * </pre>
     */
    private Integer serverId;

    /**
     * <pre>
     * 
     * 表字段 : demo_server.server_name
     * </pre>
     */
    private String serverName;

    /**
     * <pre>
     * 
     * 表字段 : demo_server.server_password
     * </pre>
     */
    private String serverPassword;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table demo_server
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * <pre>
     * 获取：
     * 表字段：demo_server.server_id
     * </pre>
     *
     * @return demo_server.server_id：
     */
    public Integer getServerId() {
        return serverId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：demo_server.server_id
     * </pre>
     *
     * @param serverId
     *            demo_server.server_id：
     */
    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：demo_server.server_name
     * </pre>
     *
     * @return demo_server.server_name：
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：demo_server.server_name
     * </pre>
     *
     * @param serverName
     *            demo_server.server_name：
     */
    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：demo_server.server_password
     * </pre>
     *
     * @return demo_server.server_password：
     */
    public String getServerPassword() {
        return serverPassword;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：demo_server.server_password
     * </pre>
     *
     * @param serverPassword
     *            demo_server.server_password：
     */
    public void setServerPassword(String serverPassword) {
        this.serverPassword = serverPassword == null ? null : serverPassword.trim();
    }

}