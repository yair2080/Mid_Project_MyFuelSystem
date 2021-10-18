package entities;
/**
 * this class represents message object- it has variables that we need to save a message.
 * we use this classe's instance in order to build the messages table and to send messages in gui.
 * every variable has its getters and setters, we call this class's constructor in 
 * "MessagesCreationC", "WriteBackMessageC", "MessagesMainC".
 */
public class Message {
	String date,sentFrom,subject,body,sendTo;

	public Message(String date, String sentFrom, String subject, String body,String sendTo) {
		super();
		this.date = date;
		this.sentFrom = sentFrom;
		this.subject = subject;
		this.body = body;
		this.sendTo=sendTo;
	}
	public Message(String sentFrom,String sendTo, String subject, String body) {
		super(); 
		this.sentFrom = sentFrom;
		this.subject = subject;
		this.body = body;
		this.sendTo=sendTo;
	}
	public Message() {}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSentFrom() {
		return sentFrom;
	}

	public void setSentFrom(String sentFrom) {
		this.sentFrom = sentFrom;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	
}
