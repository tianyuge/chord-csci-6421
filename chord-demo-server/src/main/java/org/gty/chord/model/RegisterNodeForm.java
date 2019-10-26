package org.gty.chord.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class RegisterNodeForm {

    private String address;
    private Integer port;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegisterNodeForm)) return false;
        RegisterNodeForm that = (RegisterNodeForm) o;
        return Objects.equals(address, that.address) &&
            Objects.equals(port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, port);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("address", address)
            .add("port", port)
            .toString();
    }
}
