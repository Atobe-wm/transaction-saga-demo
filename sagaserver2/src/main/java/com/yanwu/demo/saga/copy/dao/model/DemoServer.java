package com.yanwu.demo.saga.copy.dao.model;

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

    /**
     *
     * @param that
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DemoServer other = (DemoServer) that;
        return (this.getServerId() == null ? other.getServerId() == null : this.getServerId().equals(other.getServerId()))
            && (this.getServerName() == null ? other.getServerName() == null : this.getServerName().equals(other.getServerName()))
            && (this.getServerPassword() == null ? other.getServerPassword() == null : this.getServerPassword().equals(other.getServerPassword()));
    }

    /**
     *
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getServerId() == null) ? 0 : getServerId().hashCode());
        result = prime * result + ((getServerName() == null) ? 0 : getServerName().hashCode());
        result = prime * result + ((getServerPassword() == null) ? 0 : getServerPassword().hashCode());
        return result;
    }

    /**
     *
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serverId=").append(serverId);
        sb.append(", serverName=").append(serverName);
        sb.append(", serverPassword=").append(serverPassword);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}