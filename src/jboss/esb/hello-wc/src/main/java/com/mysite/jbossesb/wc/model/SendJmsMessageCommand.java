package com.mysite.jbossesb.wc.model;


public final class SendJmsMessageCommand {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SendJmsMessageCommand");
        sb.append("{message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
