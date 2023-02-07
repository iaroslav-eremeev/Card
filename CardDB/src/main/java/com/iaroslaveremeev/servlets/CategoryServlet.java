package com.iaroslaveremeev.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.dto.ResponseResult;
import com.iaroslaveremeev.model.Category;
import com.iaroslaveremeev.model.User;
import com.iaroslaveremeev.repository.CategoryRepository;
import com.iaroslaveremeev.repository.UserRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Arrays;

@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {

    /**
     * •	post – осуществляет добавление новой категории для пользователя с заданным id в базу данных
     * •	get – осуществляет получение всех категорий для заданного id пользователя, получение категории по ее id
     * •	put – осуществляет обновление категории по ее id
     * •	delete – осуществляет удаление категории и всех записей, связанных с ней
     *
     * @return
     * @throws Exception
     */

    protected void setUnicode(HttpServletRequest req, HttpServletResponse resp)
            throws UnsupportedEncodingException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setUnicode(req, resp);
        ObjectMapper objectMapper = new ObjectMapper();
        String name = req.getParameter("name");
        String userId = req.getParameter("userId");
        if(name != null && userId != null) {
            try {
                CategoryRepository categoryRepository = new CategoryRepository();
                UserRepository userRepository = new UserRepository();
                for (int i = 0; i < userRepository.getUsers().size(); i++) {
                    if (userRepository.getUsers().get(i).getId() == Integer.parseInt(userId)){
                        Category category = new Category(name, Integer.parseInt(userId));
                        categoryRepository.addUserCategory(category);
                        resp.getWriter()
                                .println(objectMapper.writeValueAsString(new ResponseResult<>(category)));
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
                    .println(objectMapper.writeValueAsString(new ResponseResult<>("Incorrect input")));
        }
    }
}
