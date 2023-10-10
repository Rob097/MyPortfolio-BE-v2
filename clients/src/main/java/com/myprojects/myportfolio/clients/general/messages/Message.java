package com.myprojects.myportfolio.clients.general.messages;

import lombok.Getter;

@Getter
public class Message implements IMessage {

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Message other = (Message) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (level != other.level)
            return false;
        if (text == null) {
            return other.text == null;
        } else return text.equals(other.text);
    }

    private String code;
    private final String text;
    private Level level;

    public Message(String text) {
        this(text, Level.INFO);
    }

    public Message(String text, Level level) {
        this.text = text;
        this.level = level;
    }

    public Message(String text, Level level, String code) {
        this.code = code;
        this.text = text;
        this.level = level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return text;
    }
}

