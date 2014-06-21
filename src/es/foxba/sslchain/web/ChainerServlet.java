package es.foxba.sslchain.web;

import se.foxba.sslchain.lib.MemoryChainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/do")
@MultipartConfig
public class ChainerServlet extends HttpServlet {
	private static final MemoryChainer memoryChainer;
	static {
		File caFile;
		String caParam = System.getProperty("sslChainerCA");
		if(caParam != null)
			caFile = new File(caParam);
		else
			caFile = new File(new File(System.getProperty("user.home")), "sslchain_ca");
		memoryChainer = new MemoryChainer(caFile);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String certificate = req.getParameter("certificate");
		final String intermediateOnlyStr = req.getParameter("intermediateOnly");
		final boolean intermediateOnly = intermediateOnlyStr != null && !intermediateOnlyStr.isEmpty();

		String result;
		try {
			result = memoryChainer.convert(certificate, intermediateOnly);
		} catch (Exception e) {
			result = null;
		}

		if(result == null || result.isEmpty()) {
			resp.sendError(400, "Bad certificate data");
		} else {
			resp.setHeader("Content-Type", "text/plain");
			PrintWriter writer = resp.getWriter();
			writer.write(result);
			writer.close();
		}
	}
}
