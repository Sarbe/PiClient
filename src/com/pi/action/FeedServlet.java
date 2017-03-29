package com.pi.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pi.bean.SchedulePayLoad;
import com.pi.feed.Feed;
import com.pi.feed.FeedMessage;
import com.pi.feed.ReadTest;
import com.pi.util.PiStatic;

/**
 * Servlet implementation class FeedServlet
 */
@WebServlet("/FeedServlet")
public class FeedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger("piclientLogger");
	private static final Logger auditlogger = Logger.getLogger("auditLogger");
       
    public FeedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		filterRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		filterRequest(request, response);
	}

	private void filterRequest(HttpServletRequest request,
			HttpServletResponse response) {
		String method = request.getParameter("method");
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		//HttpSession session = request.getSession();
		
		try {
			if(method.equals("getNewFeeds")) {
				Feed fd = ReadTest.fetchFeeds();
				List<FeedMessage> msgs = fd.getMessages();
				String feeds = gson.toJson(msgs);
				PrintWriter out = response.getWriter();
				System.out.println(feeds);
				out.write(feeds);
				
			} else {
				request.getRequestDispatcher("showImg.jsp").forward(request, response);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			logger.error(ioe.getMessage());
		} catch (ServletException se) {
			logger.error(se.getMessage());
		}
	}
}
