package com.blog.ssh.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtils {

	public static void writeJson(HttpServletResponse response, String content) throws IOException {
		String contentType = "application/json;charset=utf-8";
		write(response, content, contentType);
	}

	private static void write(HttpServletResponse response, String content,
			String contentType) throws IOException {
		response.setContentType(contentType);
		PrintWriter writer = response.getWriter();
		writer.print(content);
	}
}
