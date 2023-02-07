package com.iaroslaveremeev.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.dto.ResponseResult;
import com.iaroslaveremeev.model.User;
import com.iaroslaveremeev.repository.UserRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.NoSuchObjectException;
import java.sql.SQLException;
import java.util.Arrays;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    protected void setUnicode(HttpServletRequest req, HttpServletResponse resp)
            throws UnsupportedEncodingException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setUnicode(req, resp);
        ObjectMapper objectMapper = new ObjectMapper();
        String id = req.getParameter("id");
        try {
            UserRepository userRepository = new UserRepository();
            if (id != null){
                try {
                    User user = userRepository.getUser(Integer.parseInt(id));
                    if (user == null) throw new NoSuchObjectException("There is no user with such id!");
                    resp.getWriter()
                            .println(objectMapper.writeValueAsString(new ResponseResult<>(user)));
                }
                catch (RuntimeException | NoSuchObjectException e) {
                    resp.setStatus(400);
                    resp.getWriter()
                            .println(objectMapper.writeValueAsString(new ResponseResult<>(e.getMessage())));
                }
            }
            else {
                resp.getWriter().println(objectMapper.writeValueAsString(new ResponseResult<>(userRepository.getUsers())));
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            resp.setStatus(400);
            resp.getWriter()
                    .println(objectMapper.writeValueAsString(new ResponseResult<>(e.getMessage())));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setUnicode(req, resp);
        ObjectMapper objectMapper = new ObjectMapper();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if(login != null && password != null) {
            try {
                UserRepository userRepository = new UserRepository();
                for (int i = 0; i < userRepository.getUsers().size(); i++) {
                    User user = userRepository.getUsers().get(i);
                    String userPassword = Arrays.toString(user.getPassword())
                            .replaceAll(",", "")
                            .replaceAll("\\]", "")
                            .replaceAll("\\[", "")
                            .replaceAll(" ", "");
                    if (login.equals(user.getLogin()) &&
                            password.equals(userPassword)){
                        resp.getWriter()
                                .println(objectMapper.writeValueAsString(new ResponseResult<>(user)));
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                resp.setStatus(400);
                resp.getWriter()
                        .println(objectMapper.writeValueAsString(new ResponseResult<>(e.getMessage())));
            }
        }
        else {
            resp.setStatus(400);
            resp.getWriter()
                    .println(objectMapper.writeValueAsString(new ResponseResult<>("Incorrect login or password")));
        }
    }

    //TODO Дописать удаление всей информации связанной с юзером (когда будут готовы карточки и категории)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setUnicode(req, resp);
        ObjectMapper objectMapper = new ObjectMapper();
        String id = req.getParameter("id");
        try {
            UserRepository userRepository = new UserRepository();
            if(id != null){
                try {
                    User userToDelete = userRepository.getUser(Integer.parseInt(id));
                    if (userToDelete == null) throw new NoSuchObjectException("No user with such id!");
                    userRepository.delete(Integer.parseInt(id));
                    resp.getWriter()
                            .println(objectMapper.writeValueAsString(new ResponseResult<>(userToDelete)));
                } catch (RuntimeException | NoSuchObjectException e) {
                    resp.setStatus(400);
                    resp.getWriter()
                            .println(objectMapper.writeValueAsString(new ResponseResult<>(e.getMessage())));
                }
            }
        } catch (Exception e) {
            resp.getWriter().println(objectMapper.writeValueAsString(new ResponseResult<>(e.getMessage())));
        }
    }
}
