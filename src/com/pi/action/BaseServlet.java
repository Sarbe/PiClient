package com.pi.action;

import java.io.IOException;
import java.net.SocketException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pi.util.CommonFunction;
import com.pi.util.Config;
import com.pi.util.PiStatic;

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public BaseServlet() {
		super();
	}
	@Override
	public void init() throws ServletException {
		//System.out.println("CustomServlet.init()");
		try {
				// Retrieve MAC address of the system
				if(Config.getKey("env").equals(PiStatic.env_PI))
					PiStatic.macAdd = CommonFunction.searchForMac();
				else
					PiStatic.macAdd = "6C-71-D9-99-A6-A7";
			} catch (SocketException e) {
				e.printStackTrace();
			}
		
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		filterRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		filterRequest(request, response);
	}

	public void filterRequest(HttpServletRequest request,
			HttpServletResponse response) {
		//System.out.println("CustomServlet.filterRequest()");
		try {
			
			String method = String.valueOf(request.getParameter("method"));
			HttpSession session = request.getSession();

			if (method.equals("login")) {
				String userName = request.getParameter("userName");
				String password = request.getParameter("password");
				if (userName.equals(password)) {
					session.setAttribute("USER", true);
					response.sendRedirect("pages/piweb.jsp");
				}
			}else if (method.equals("logout")) {
				session.removeAttribute("USER");
				session.invalidate();
				response.sendRedirect("pages/start.jsp");
			} else {
				boolean isValid =  (Boolean) (session.getAttribute("USER")==null?false:session.getAttribute("USER"));
				if (isValid) {
					execute(request, response);
				} else {
					if(method.equals("refreshImg")){
						execute(request, response);
					}else{
						response.sendRedirect("pages/start.jsp");
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public abstract void execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException;
}
