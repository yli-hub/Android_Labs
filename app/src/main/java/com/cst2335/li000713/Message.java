package com.cst2335.li000713;

public class Message {
    protected long id;
    private boolean isSend;
    private String msg;



    public Message(String msg, boolean isSend, long id) {
        this.msg = msg;
        this.isSend = isSend;
        this.id = id;
    }

    public boolean getIsSend(){
        return isSend;
    }

    public long getId(){
        return id;
    }

    public String getMsg() {
        return msg;
    }

}


