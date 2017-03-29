	package com.pi.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.pi.bean.SchedulePayLoad;
import com.pi.util.CommonFunction;
import com.pi.util.Config;
import com.pi.util.PiStatic;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Servlet implementation class PiClient
 */
@WebServlet("/MediaUploadServlet")
public class MediaUploadServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final Logger schdLogger = Logger.getLogger("schdLogger");

	private boolean isMultipart;
	private int maxFileSize = 20*1024 * 1024; // 10MB
	private int maxMemSize = 10 * 1024; // 10KB
	
	

	public MediaUploadServlet() {
		super();
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}


	public void execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String msg = "";
		
		isMultipart = ServletFileUpload.isMultipartContent(request);
		
		String schdId = String.valueOf(System.currentTimeMillis());
		
//		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		if (isMultipart) {

			DiskFileItemFactory factory = new DiskFileItemFactory();
			// maximum size that will be stored in memory
			factory.setSizeThreshold(maxMemSize);
			// Location to save data that is larger than maxMemSize.
			factory.setRepository(new File(PiStatic.imgDir));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			// maximum file size to be uploaded.
			upload.setSizeMax(maxFileSize);
			SchedulePayLoad sp = new SchedulePayLoad();
			String allowedExtn = Config.getKey("allowdImgExtn");
			try {

				// Parse the request to get file items.
				List fileItems = upload.parseRequest(request);
				// Process the uploaded file items
				Iterator i = fileItems.iterator();

				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					if (!fi.isFormField()) {
						// Get the uploaded file parameters
						String fieldName = fi.getFieldName();
						String fileName = fi.getName();
						String contentType = fi.getContentType();
						boolean isInMemory = fi.isInMemory();
						long sizeInBytes = fi.getSize();
						
						
						String mediaFileNm = "";
						// Write the file
						if (fileName.lastIndexOf(File.separator) >= 0) {
							mediaFileNm = fileName.substring(fileName.lastIndexOf(File.separator));
							//file = new File(PiStatic.imgDir+ fileName.substring(fileName.lastIndexOf(File.separator)));
						} else {
							mediaFileNm = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
							//file = new File(PiStatic.imgDir + fileName.substring(fileName.lastIndexOf(File.separator) + 1));
						}
						String extn = mediaFileNm.substring(mediaFileNm.lastIndexOf(".")+1) ;
						
						if(allowedExtn.indexOf(extn.toUpperCase())!=-1){
							File file = new File(PiStatic.imgDir + mediaFileNm);
							// write
							fi.write(file);
						//String extn = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")+1, file.getAbsolutePath().length()) ;
			        		sp.setFileName(fileName);
							sp.setFileType(contentType);
							sp.setScheduleId(schdId);
							sp.setMimeType(extn.toLowerCase());
							//sp.setMedia(imagteToByte(file, extn.toUpperCase()));
			        	}else{
			        		throw new Exception("Only " + allowedExtn + " is allowed");
			        	}
					}else{
						if(fi.getFieldName().equals("scheduleName")){
						}else if(fi.getFieldName().equals("startDate")){
							sp.setStartDate(fi.getString());
						}else if(fi.getFieldName().equals("endDate")){
							sp.setEndDate(fi.getString());
						}else if(fi.getFieldName().equals("timeOfDay")){
							sp.setTimeOfDay(fi.getString());
						}else if(fi.getFieldName().equals("duration")){
							sp.setDuration(Integer.valueOf(fi.getString()).intValue());
						}else if(fi.getFieldName().equals("priority")){
							sp.setPriority(Integer.valueOf(fi.getString()).intValue());
						}
					}
				}
				// Save to Memory and write to file
				List<SchedulePayLoad> schDtls = new ArrayList<SchedulePayLoad>();
				schDtls.add(sp);
				
				CommonFunction.syncScheduleDtl(schDtls);
				Gson g = new Gson();
				schdLogger.info("ADD::"+g.toJson(sp));
				msg += "Media Uploaded Successfully";
			} catch (Exception ex) {
				//System.out.println(ex.getMessage());
				msg += ex.getMessage();
			}finally{
				request.setAttribute("MSG", msg);
			}
			response.sendRedirect("pages/piweb.jsp");
		}
		
	}
	public byte[] imagteToByte(File file2,String fileType)  {
		// byte[] res=baos.toByteArray();
		String encodedImage=null;
		BufferedImage image;
		ByteArrayOutputStream baos;
		
		try {
			image = ImageIO.read(file2);
			baos = new ByteArrayOutputStream();
			ImageIO.write(image, fileType, baos);
			encodedImage = Base64.encode(baos.toByteArray());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			image=null;
			baos=null;
		}
		return encodedImage.getBytes();
	}

}
