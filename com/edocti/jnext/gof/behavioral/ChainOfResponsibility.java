package com.edocti.jnext.gof.behavioral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

class Email {
	String from, to, cc;
	String subject;
	String message;
	
	public Email(String from, String to, String cc, String subject, String message) {
		this.from = from;
		this.to= to;
		this.cc = cc;
		this.subject = subject;
		this.message = message;
	}
	
	@Override public String toString() { return "from: " + from + ", to: " + to; }
}

class DefaultFilter extends Filter {
	public DefaultFilter(Outlook o) {
		super(o);
	}

	@Override
	boolean filter(Email e) {
		o.getFolder("inbox").add(e);
		return true;
	}
}

class FromFilter extends Filter {
	private String from;
	private String folderName;
	private boolean continueProcessing;
	
	public FromFilter(String from, String folder, boolean continueProcessing, Outlook o) {
		super(o);
		this.from = from;
		this.folderName = folder;
		this.continueProcessing = continueProcessing;
	}
	
	@Override
	boolean filter(Email e) {
		if (e.from.equalsIgnoreCase(this.from)) {
			List<Email> folder = o.getFolder(folderName);
			if (folder != null) {
				folder.add(e);
				return !continueProcessing;
			}
		}
		return false;
	}
}

class Filter {
	protected Outlook o;
	
	public Filter(Outlook o) {
		this.o = o;
//		df = new DefaultFilter(o);
	}
	
	List<Filter> filters = new CopyOnWriteArrayList<>();
	
	boolean filter(Email e) { return false; }
	
	void handle(Email e) {
		boolean processed = false;
		for (Filter f : filters) {
			processed = f.filter(e);
			if (processed) {
				break;  // filter processed email and decided that chain should stop
			}
		}
		if (!processed)
			o.getFolder("inbox").add(e);
//		if (!processed) {
//			df.filter(e);
//		}
	}
	
	void add (Filter f) {
		filters.add(f);
	}
	
	void remove(Filter f) {
		filters.remove(f);
	}
}



class Outlook {
	Map<String, List<Email>> folders;
	Filter rootFilter;

	public Outlook() {
		folders = new HashMap<>();
//		folders.put("organizational", new CopyOnWriteArrayList<>());
//		folders.put("spam", new CopyOnWriteArrayList<>());
//		folders.put("inbox", new CopyOnWriteArrayList<>());
		rootFilter = new Filter(this);
	}
	
	public List<Email> getFolder(String folderName) {
		return folders.get(folderName);
	}
	
	public void emailReceived(Email e) {
		rootFilter.handle(e);
	}
	
	public void createNewFilter(Filter f) {
		if (f != null)
			rootFilter.add(f);
	}
	
	public void showFolders() {
		System.out.println(folders.toString());
	}
}


public class ChainOfResponsibility {
	public static void main(String[] args) {
		Outlook outlook = new Outlook();
		outlook.createNewFilter(new FromFilter("alina.andrei@aci.com", "organizational", false, outlook));
		outlook.createNewFilter(new FromFilter("gigi@gmail.com", "spam", false, outlook));
		outlook.createNewFilter(new FromFilter("spam@spam.com", "spam", false, outlook));
		
		outlook.emailReceived(new Email("alina.andrei@aci.com", "cucu@microsoft.com", null, null, "Hello!"));
		outlook.emailReceived(new Email("alina.andrei@aci.com", "cucu@microsoft.com", null, null, "Hello!"));
		outlook.emailReceived(new Email("spam@spam.com", "cucu@microsoft.com", null, null, "Hello!"));
		outlook.emailReceived(new Email("gigi@gmail.com", "cucu@microsoft.com", null, null, "Hello!"));
		outlook.emailReceived(new Email("bill.gates@microsoft.com", "cucu@microsoft.com", null, null, "Hello!"));
		
		outlook.showFolders();
	}
}
