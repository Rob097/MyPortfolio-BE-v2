package com.myprojects.myportfolio.clients.general.messages;

import org.springframework.util.Assert;

import java.util.*;

/**
 * @author Roberto97
 * Class used to incapsulate the content returned to the FE and also the messages (IMessage).
 * This class is used for collection. For single objects call MessageResource.
 * @param <T>
 *
 */
public class MessageResources<T> extends MessageSupport implements Iterable<T> {

    private final Collection<T> content;


    public MessageResources(Iterable<T> content, Iterable<? extends IMessage> messages) {

        Assert.notNull(content, "Content must not be null!");

        this.content = new ArrayList<>();

        for (T element : content) {
            this.content.add(element);
        }
        if (messages != null) {
            this.add(messages);
        }
    }

    /**
     * Returns the underlying elements.
     *
     * @return the content will never be null.
     */
    public Collection<T> getContent() {
        return Collections.unmodifiableCollection(content);
    }


    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }


    @Override
    public String toString() {
        return String.format("MessageResources { content: %s, %s }", getContent(), super.toString());
    }


    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }

        MessageResources<?> that = (MessageResources<?>) obj;

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