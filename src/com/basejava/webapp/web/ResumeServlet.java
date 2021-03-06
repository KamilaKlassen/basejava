package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        Writer writer = response.getWriter();
        writer.write(
                """
                        <!DOCTYPE html>
                        <html>
                        <head>
                            <meta charset="UTF-8">
                            <title>Список резюме</title>
                            <style>
                                table, th, td {
                                    border: 1px solid black;
                                    border-collapse: collapse;
                                }

                                th,td {
                                    padding: 5px;
                                }
                            </style>
                        </head>
                        <body>
                        <table>
                            <tr>
                                <th>Имя</th>
                                <th>Uuid</th>
                            </tr>"""
        );
        for (Resume resume : storage.getAllSorted()) {
            writer.write("<tr>\n" +
                    "  <td>" + resume.getFullName() + "</td>\n" +
                    "  <td>" + resume.getUuid() + "</td>\n" +
                    "   </tr>"
            );
        }
        writer.write("""
                </table>
                </body>
                </html>
                """);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
