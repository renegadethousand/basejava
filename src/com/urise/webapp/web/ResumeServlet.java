package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    SqlStorage sqlStorage;

    @Override
    public void init() throws ServletException {
        sqlStorage = (SqlStorage) Config.get().getSqlStorage();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        final String name = request.getParameter("name");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        if (name == null) {
            response.getWriter().write("Hello Resumes!");
        } else {
            response.getWriter().write("Hello " + name + "!");
        }

        final List<Resume> resumeList = sqlStorage.getAllSorted();
        response.getWriter().write("<table>");
        response.getWriter().write("<tr>");
        response.getWriter().write("<th>ID</th>");
        response.getWriter().write("<th>Full name</th>");
        response.getWriter().write("</tr>");
        for (Resume resume : resumeList) {
            response.getWriter().write("<tr>");
            response.getWriter().write("<td>" + resume.getUuid() + "</td>");
            response.getWriter().write("<td>" + resume.getFullName() + "</td>");
            response.getWriter().write("</tr>");
        }
        response.getWriter().write("</table>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
