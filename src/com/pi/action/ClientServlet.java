package com.pi.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.pi.bean.SchedulePayLoad;
import com.pi.service.PiService;
import com.pi.service.Impl.PiServiceImpl;
import com.pi.util.CommonFunction;
import com.pi.util.GSONHandler;
import com.pi.util.PiStatic;

@WebServlet("/ClientServlet")
public class ClientServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger("piclientLogger");
	private static final Logger schdLogger = Logger.getLogger("schdLogger");
	PiService ps = new PiServiceImpl();
	public ClientServlet() {
		super();
	}
	@Override
	public void init() throws ServletException {
		super.init();
		//System.out.println("ClientServlet.init()");
		try {
			// Read locally saved schd details
			ps.getLocalSchedules();
			//List<SchedulePayLoad> schList = GSONHandler.schdFrmJSONFileToObjLst(PiStatic.jsonDir + PiStatic.schdJsonFile);
			//PiStatic.setMemory(schList);
			
			
			// ////////////
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error initializing Client" + e.getMessage());
		}
	}
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("ClientServlet.execute()");
		String method = request.getParameter("method");
		
		try {
			
			if (method.equals("deleteSchedule")) {

				String scheduleId = request.getParameter("selectedSchedule");
				if (scheduleId != null && !scheduleId.equals("")) {
					List<String> schds = new ArrayList<String>();
					schds.add(scheduleId);
					//CommonFunction.removeAndSyncScheduleDtl(schds);
					ps.removeAndSyncScheduleDtl(schds);
					Gson g = new Gson();
					schdLogger.info("DELETE::"+scheduleId);
				}

				logger.info("Schedule deleted");
				
				response.sendRedirect("pages/piweb.jsp");
			} else if (method.equals("retrieveOldSchd")) {
				logger.info("ClientServlet.execute()-retrieveOldSchd called ");
				//CommonFunction.retrieveServerOldContent(PiStatic.macAdd);
				ps.retrieveServerOldContent();
				
				response.sendRedirect("pages/piweb.jsp");
				
			} else {
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			logger.error(ioe.getMessage());
		} catch (ServletException se) {
			logger.error(se.getMessage());
		}
	}
}
