package datatype;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private char[] message = new char[500];
    private List<String> referredTopics;
    private int length;

    public Message(char[] m, List<String> referredTopics) {
        System.arraycopy(m, 0, message, 0, m.length);
        length = m.length;
        this.referredTopics = referredTopics;
    }

    public Message() {
        referredTopics = new ArrayList<String>();
        length = 0;
    }

    public char[] getMessage() {
        return message;
    }

    public List<String> getReferredTopics() {
        return referredTopics;
    }

    public void reset(char[] m, int offset, int length, List<String> referredTopics) {
        System.arraycopy(m, offset, message, 0, length);
        this.length = length;
        this.referredTopics = referredTopics;
    }

    public int getLength() {
        return length;
    }

    public char charAt(int index) {
        return message[index];
    }
}
