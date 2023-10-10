package com.myprojects.myportfolio.clients.general.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;


/**
 * @author Roberto97
 * Class used to incapsulate the content returned to the FE and also the messages (IMessage).
 * This class is used for single objects. For collection call MessageResources.
 * @param <T>
 *
 */
@Getter
@NoArgsConstructor
public class MessageResource<T> extends MessageSupport {

    /**
     * Content returned to FE. It could be of any type as long as is not a collection
     * -- GETTER --
     *  Returns the underlying entity.
     *

     */
    private T content;


    /**
     * Creates a new Resource with the given content and messages.
     *
     * @param content must not be null.
     * @param messages the messages to add to the Resource.
     */
    public MessageResource(T content, IMessage... messages) {
        this(content, Arrays.asList(messages));
    }

    public MessageResource(T content, Iterable<? extends IMessage> messages) {

        //Assert.notNull(content, "Content must not be null!");
        Assert.isTrue((!(content instanceof Collection)), "Content must not be a collection! Use Resources instead!");
        this.content = content;
        this.add(messages);
    }

    public MessageResource(T content) {
//    	Assert.notNull(content, "Content must not be null!");
        Assert.isTrue((!(content instanceof Collection)), "Content must not be a collection! Use MessageResources instead!");
        this.content = content;
    }


    @Override
    public String toString() {
        return String.format("MessageResource { content: %s, %s }", getContent(), super.toString());
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }

        MessageResource<?> that = (MessageResource<?>) obj;

        boolean contentEqual = Objects.equals(this.content, that.content);
        return contentEqual && super.equals(obj);
    }


    @Override
    public int hashCode() {

        int result = super.hashCode();
        result += content == null ? 0 : 17 * content.hashCode();
        return result;
    }
}
