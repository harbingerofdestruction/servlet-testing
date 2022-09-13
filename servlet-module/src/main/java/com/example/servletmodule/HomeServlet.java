package com.example.servletmodule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

@WebServlet(name = "HomeServlet", urlPatterns = {"/explorer"})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String currentPath = req.getParameter("path");

        if(currentPath == null) {
            currentPath = System.getProperty("os.name").toLowerCase().startsWith("win") ? "C:/" : "/opt/tomcat/";
        }

        showFiles(req, new File(currentPath).listFiles(), currentPath);
        req.setAttribute("currentPath", currentPath);
        req.getRequestDispatcher("MyPage.jsp").forward(req, resp);
    }
    private void showFiles(HttpServletRequest req, File[] files, String currentPath) {
        StringBuilder attrFolders = new StringBuilder();
        StringBuilder attrFiles = new StringBuilder();
        for (File file : files) {
            if (file.isDirectory()) {
                attrFolders.append("<li><a href=\"?path=").append(currentPath).append("/").append(file.getName())
                        .append("\">")
                        .append(file.getName())
                        .append("</a></li>");
            } else {
                attrFiles.append("<p><a href=\"").append(currentPath).append("/").append(file.getName())
                        .append("\" download=\"\">")
                        .append(file.getName())
                        .append("</a></p>");
            }
        }
        req.setAttribute("folders", attrFolders);
        req.setAttribute("files", attrFiles);
    }
}

