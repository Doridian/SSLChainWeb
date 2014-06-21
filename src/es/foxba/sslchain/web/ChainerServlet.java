package es.foxba.sslchain.web;

import se.foxba.sslchain.lib.MemoryChainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/do")
public class ChainerServlet extends HttpServlet {
	private static final MemoryChainer memoryChainer;
	static {
		File caFile;
		String caParam = System.getProperty("sslChainerCA");
		if(caParam != null)
			caFile = new File(caParam);
		else
			caFile = new File("/etc/ssl/certs");
		memoryChainer = new MemoryChainer(caFile);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String certificate = req.getParameter("certificate");
		String intermediateOnlyStr = req.getParameter("intermediateOnly");
		boolean intermediateOnly = intermediateOnlyStr != null && !intermediateOnlyStr.isEmpty();
		String result = memoryChainer.convert(certificate, intermediateOnly);
		resp.setHeader("Content-Type", "text/plain");
		resp.getWriter().write(result);
	}
}
