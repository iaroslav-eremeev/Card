package com.iaroslaveremeev.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.dto.ResponseResult;
import com.iaroslaveremeev.model.User;
import com.iaroslaveremeev.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    protected void setUnicode(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setUnicode(req, resp);
        ObjectMapper objectMapper = new ObjectMapper();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        if(login != null && password != null && name != null) {
            try(UserRepository userRepository = new UserRepository()) {
                User user = new User(login, password.toCharArray(), name);
                if (userRepository.add(user)) {
                    resp.getWriter()
                            .println(objectMapper.writeValueAsString(new ResponseResult<>(user)));
                }
                else {
                    throw new RuntimeException("User not registered");
                }
            } catch (Exception e) {
                resp.setStatus(400);
                resp.getWriter()
                        .println(objectMapper.writeValueAsString(new ResponseResult<>(e.getMessage())));
            }
        }
    }
}
