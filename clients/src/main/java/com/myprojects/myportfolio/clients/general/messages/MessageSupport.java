package com.myprojects.myportfolio.clients.general.messages;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Roberto97
 * Class extended by MessageResource and MEssageResources.
 */
@Getter
public class MessageSupport {
    /**
     * -- GETTER --
     *  Returns all IMessages contained in this resource.
     *
     */
    private final List<IMessage> messages;

    public MessageSupport() {
        this.messages = new ArrayList<>();
    }

    /**
     * Adds the given message to the resource.
     *
     */
    public void add(IMessage message) {
        Assert.notNull(message, "Message must not be null!");
        this.messages.add(message);
    }

    /**
     * Adds all given IMessages to the resource.
     *
     */
    public void add(Iterable<? extends IMessage> messages) {
        Assert.notNull(messages, "Given messages must not be null!");
        for (IMessage candidate : messages) {
            add(candidate);
        }
    }

    /**
     * Adds all given IMessages to the resource.
     *
     * @param messages must not be null.
     */
    public void add(IMessage... messages) {
        Assert.notNull(messages, "Given messages must not be null!");
        add(Arrays.asList(messages));
    }


    @Override
    public String toString() {
        return String.format("messages: %s", messages);
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || !obj.getClass().equals(this.getClass())) {
            return false;
        }

        MessageSupport that = (MessageSupport) obj;

        return this.messages.equals(that.messages);
    }


    @Override
    public int hashCode() {
        return this.messages.hashCode();
    }
}
