package servlet.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/MediaContainer/UploadFiles")
public class UploadFiles extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (request.getSession().getAttribute("userId") == null)
			return;
		int userId = Integer.parseInt(request.getSession()
				.getAttribute("userId").toString());

		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new IllegalArgumentException(
					"Request is not multipart, please 'multipart/form-data' enctype for your form.");
		}

		ServletFileUpload uploadHandler = new ServletFileUpload(
				new DiskFileItemFactory());
		PrintWriter writer = response.getWriter();
		response.setContentType("application/json");
		JSONArray json = new JSONArray();
		JSONObject fileListJson = new JSONObject();

		try {
			// Write files.
			List<FileItem> items = uploadHandler.parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					File file = new File(request.getServletContext()
							.getRealPath("/") + "UserFiles/" + userId + "/",
							item.getName());
					String filenameWithoutExtension = FilenameUtils
							.getBaseName(item.getName());
					String extension = FilenameUtils.getExtension(item
							.getName());

					for (int i = 1; file.exists(); i++) {
						file = new File(
								request.getServletContext().getRealPath("/")
										+ "UserFiles/" + userId + "/",
								filenameWithoutExtension + "_" + i + "."
										+ extension);
					}

					item.write(file);

					// Test code.
					JSONObject jsono = new JSONObject();
					jsono.put("name", item.getName());
					jsono.put("size", item.getSize());
					jsono.put("url", "UploadServlet?getfile=" + item.getName());
					jsono.put("thumbnail_url", "/2730_project/UserFiles/"
							+ userId + "/" + item.getName());
					jsono.put("delete_url",
							"MediaContainer/DeleteFile?delfile=" + item.getName());
					jsono.put("delete_type", "GET");
					json.put(jsono);					
				}
			}
			fileListJson.put("files", json);
		} catch (FileUploadException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// Output file info.
			writer.write(fileListJson.toString());
			writer.close();
		}
	}

}
