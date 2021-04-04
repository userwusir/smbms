package com.wll.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.wll.pojo.Role;
import com.wll.pojo.User;
import com.wll.service.role.RoleService;
import com.wll.service.role.RoleServiceImpl;
import com.wll.service.user.UserService;
import com.wll.service.user.UserServiceImpl;
import com.wll.util.Constant;
import com.wll.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wulele
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getParameter("method");
        if ("pwdmodify".equals(method)) {
            this.pwdModify(req, resp);
        } else if ("savepwd".equals(method)) {
            this.alertPassword(req, resp);
        } else if ("query".equals(method)) {
            this.query(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //验证老密码
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Object attribute = req.getSession().getAttribute(Constant.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");
        HashMap<String, String> hashMap = new HashMap<>();
        if (attribute == null) {
            //session过期
            hashMap.put("result", "sessionerror");
        } else if (oldpassword == null) {
            //输入密码为空
            hashMap.put("result", "error");
        } else {
            String userPassword = ((User) attribute).getUserPassword();
            if (userPassword.equals(oldpassword)) {
                //密码正确
                hashMap.put("result", "true");
            } else {
                //密码错误
                hashMap.put("result", "false");
            }
        }
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(hashMap));
        writer.flush();
        writer.close();
    }

    //修改密码
    public void alertPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //登录时user的所有信息保存在了session中
        Object attribute = req.getSession().getAttribute(Constant.USER_SESSION);
        //jsp修改密码页面获取新密码
        String newpassword = req.getParameter("newpassword");
        boolean flag = false;
        if (attribute != null && newpassword != null) {
            UserService userService = new UserServiceImpl();
            flag = userService.alertPsw(((User) attribute).getId(), newpassword);
            if (flag) {
                req.setAttribute(Constant.SYS_MESSAGE, "密码修改成功，请返回重新登录");
                req.getSession().removeAttribute(Constant.USER_SESSION);
            } else {
                req.setAttribute(Constant.SYS_MESSAGE, "密码修改失败");
            }
        } else {
            req.setAttribute(Constant.SYS_MESSAGE, "新密码有问腿");
        }
        req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
    }

    public void query(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = new UserServiceImpl();
        RoleService roleService = new RoleServiceImpl();
        List<User> userList = new ArrayList<User>();
        List<Role> roleList = null;
        //用户姓名
        String userName = req.getParameter("queryname");
        //用户role
        String queryUserRole = req.getParameter("queryUserRole");
        //页标
        String pageIndex = req.getParameter("pageIndex");
        //默认用户role为0
        int userRole = 0;
        //设置默认页面行数
        int pageSize = Constant.pageSize;
        //设置默认当前页
        int currentPageNow = 1;
        if (userName == null) {
            userName = "";
        }
        if (queryUserRole != null && !"".equals(queryUserRole)) {
            userRole = Integer.parseInt(queryUserRole);
        }
        if (pageIndex != null) {
            try {
                currentPageNow = Integer.parseInt(pageIndex);
            } catch (NumberFormatException e) {
                resp.sendRedirect("error.jsp");
            }
        }

        //获取用户数量
        int totalCount = userService.getUserCount(userName, userRole);

        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNow);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);
        //获取总页数
        int totalPageCount = pageSupport.getTotalPageCount();
        //页首页尾
        if (currentPageNow < 1) {
            currentPageNow = 1;
        } else if (currentPageNow > totalPageCount) {
            currentPageNow = totalPageCount;
        }

        userList = userService.getUserinfoList(userName, userRole, currentPageNow, pageSize);
        roleList = roleService.getRoleList();
        req.setAttribute("userList", userList);
        req.setAttribute("roleList", roleList);
        req.setAttribute("queryUserName", userName);
        req.setAttribute("queryUserRole", userRole);
        req.setAttribute("totalPageCount", totalPageCount);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPageNo", currentPageNow);

        req.getRequestDispatcher("userlist.jsp").forward(req, resp);
    }
}
