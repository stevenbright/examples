package com.mysite.jbossesb.wc.model;

/**
 * Represents standalone ESB message.
 */
public final class SendEsbMessageCommand {
    private String serviceCategory;

    private String serviceName;

    private String message;



    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SendEsbMessageCommand");
        sb.append("{serviceCategory='").append(serviceCategory).append('\'');
        sb.append(", serviceName='").append(serviceName).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
