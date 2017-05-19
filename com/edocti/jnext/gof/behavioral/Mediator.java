package com.edocti.jnext.gof.behavioral;

class Model {
	double value;
}

abstract class View {
	abstract void show(Model m);
}

abstract class Controller {
	abstract String process(Model m, double val);
}

class SimpleView extends View {
	@Override
	void show(Model m) {
		System.out.println(m.value);
	}
}

class AddController extends Controller {
	@Override
	String process(Model m, double val) {
		m.value += val;
		return "simple";
	}
}

// mediator
class DispatcherServlet {
	Controller add = new AddController();
	Model m = new Model();
	
	public String processRequest(String request, double value) {
		if (request.equals("add")) {
			return add.process(m, value);
		}
		throw new IllegalArgumentException("Nu cunosc");
	}
	
	public void showView(String viewName) {
		if (viewName.equals("simple"))
			new SimpleView().show(m);
	}
}


public class Mediator {
	public static void main(String[] args) {
		DispatcherServlet mediator = new DispatcherServlet();
		String viewName = mediator.processRequest("add", 20);
		mediator.showView(viewName);
		viewName = mediator.processRequest("add", 20);
		mediator.showView(viewName);
	}
}
