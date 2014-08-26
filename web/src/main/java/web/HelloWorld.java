package web;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "HelloWorld", urlPatterns = { "/test" })
public class HelloWorld extends HttpServlet {

	private static final long serialVersionUID = 0L;

	private String message;

	public void init() throws ServletException {

		message = "Hello World";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println("<h1>" + message + "</h1>");
	}

	public void destroy() {
		
	}
}
