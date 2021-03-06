package com.yanwu.demo.saga.client.dao.model;

import java.io.Serializable;

public class DemoClient implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column demo_client.client_id
     *
     * @mbggenerated
     */
    private Integer clientId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column demo_client.client_name
     *
     * @mbggenerated
     */
    private String clientName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column demo_client.client_password
     *
     * @mbggenerated
     */
    private String clientPassword;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table demo_client
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column demo_client.client_id
     *
     * @return the value of demo_client.client_id
     *
     * @mbggenerated
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column demo_client.client_id
     *
     * @param clientId the value for demo_client.client_id
     *
     * @mbggenerated
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column demo_client.client_name
     *
     * @return the value of demo_client.client_name
     *
     * @mbggenerated
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column demo_client.client_name
     *
     * @param clientName the value for demo_client.client_name
     *
     * @mbggenerated
     */
    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column demo_client.client_password
     *
     * @return the value of demo_client.client_password
     *
     * @mbggenerated
     */
    public String getClientPassword() {
        return clientPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column demo_client.client_password
     *
     * @param clientPassword the value for demo_client.client_password
     *
     * @mbggenerated
     */
    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword == null ? null : clientPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demo_client
     *
     * @mbggenerated
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
        DemoClient other = (DemoClient) that;
        return (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getClientName() == null ? other.getClientName() == null : this.getClientName().equals(other.getClientName()))
            && (this.getClientPassword() == null ? other.getClientPassword() == null : this.getClientPassword().equals(other.getClientPassword()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demo_client
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getClientName() == null) ? 0 : getClientName().hashCode());
        result = prime * result + ((getClientPassword() == null) ? 0 : getClientPassword().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demo_client
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", clientId=").append(clientId);
        sb.append(", clientName=").append(clientName);
        sb.append(", clientPassword=").append(clientPassword);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}