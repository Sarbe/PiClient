package com.pi.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pi.bean.SchedulePayLoad;
import com.pi.service.PiService;
import com.pi.service.Impl.PiServiceImpl;
import com.pi.task.PayLoadManager;
import com.pi.util.PiStatic;

@WebServlet("/ScheduleServlet")
public class ScheduleServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger("piclientLogger");
	private static final Logger auditlogger = Logger.getLogger("auditLogger");
	
	private PayLoadManager payldMngr = null;
	PiService ps = new PiServiceImpl();
	public ScheduleServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		//System.out.println("ScheduleServlet.init()");
		try {
			// Read locally saved schd details
			ps.getLocalSchedules();
			//List<SchedulePayLoad> schList =  //GSONHandler.schdFrmJSONFileToObjLst(PiStatic.jsonDir + PiStatic.schdJsonFile);
			//PiStatic.setMemory(schList);
			//logger.info("Schedute size after reading local file :: " + PiStatic.getMemory().size());
			
			// Starting Timer
			payldMngr = new PayLoadManager(PiStatic.macAdd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("Error initializing Client" + e.getMessage());
		}
	}

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("ScheduleServlet.execute()");
		String method = request.getParameter("method");
		//HttpSession session = request.getSession();
		
		try {
			if (method.equals("refreshImg")) {
				logger.debug("Refreshing Image.");
				//Set the last refresh Time
				PiStatic.lastImgRefreshTime = new Date();

				SchedulePayLoad schd = ps.getNxtSchdForAdWindow();
				String str = "";
				PrintWriter out = response.getWriter();
				if(schd.getScheduleId()!= null){
					Gson gson = new GsonBuilder().disableHtmlEscaping().create();
					str = gson.toJson(schd);
					logger.info("Image date retrieved for new image :: " + str);
					auditlogger.info(str);
				}
				out.write(str);

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
