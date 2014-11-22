package org.docs.richfaces;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.richfaces.application.push.MessageException;
import org.richfaces.application.push.TopicKey;
import org.richfaces.application.push.TopicsContext;

@ViewScoped
@ManagedBean
public class RichBean implements Serializable, Runnable {

    private static final long serialVersionUID = -6239437588285327644L;
    private int cont = 0;

    private String name = "oioi";
    private Thread thread;
    

    @PostConstruct
    public void postContruct() {
        name = "John";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Date getDate() {
        return new Date();
    }
    public String getCiro(){
    	return "ciro" + cont++;
    }
     
    public void push() throws MessageException {
        TopicKey topicKey = new TopicKey("sampleAddress");
        TopicsContext topicsContext = TopicsContext.lookup();
      
        topicsContext.publish(topicKey, "empty message");
      
        System.out.println("push event");
        
        if (thread == null) {
            thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();
        }       
    }

	@Override
	public void run() {
		 while (thread != null) {
	            try {
	              this.push();
	                Thread.sleep(1000);
	                System.out.println("oiii");
	            } catch (InterruptedException e) {
	                // TODO Auto-generated catch block
	                // e.printStackTrace();
	            } catch (MessageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		
	}
}