package com.sunilOS.ORSProject3.util;

/**
 * @author Anshul
 *
 */
public class EmailMessage {
	/**
     * Contains comma separated TO addresses
     */
    private String to = null;

    /**
     * Sender addresses
     */
    private String from = null;

    /**
     * Contains comma separated CC addresses
     */
    private String cc = null;

    /**
     * Contains comma separated BCC addresses
     */
    private String bcc = null;

    /**
     * Contains message subject
     */
    private String subject = null;

    /**
     * Contains message
     */
    private String message = null;

    /**
     * HTML message constant
     */
    public static final int HTML_MSG = 1;

    /**
     * Text message constant
     */
    public static final int TEXT_MSG = 2;

    /**
     * Message type (HTML/text), default type is text
     */
    private int messageType = TEXT_MSG;

    
    /*
     * accessor
     */
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
