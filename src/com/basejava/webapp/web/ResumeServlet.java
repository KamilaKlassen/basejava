package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {

    private final Storage storage = Config.get().getStorage();

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
                                <th>E-Mail</th>
                            </tr>"""
        );
        for (Resume resume : storage.getAllSorted()) {
            writer.write("<tr>\n" +
                    "  <th>" + resume.getFullName() + "</th>\n" +
                    "  <th>" + resume.getUuid() + "</th>\n" +
                    "   </tr>"
            );
            writer.write("""
                    </table>
                    </body>
                    </html>
                    """);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
