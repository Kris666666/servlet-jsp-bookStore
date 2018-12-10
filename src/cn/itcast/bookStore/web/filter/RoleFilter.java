package cn.itcast.bookStore.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookStore.domain.User;

public class RoleFilter implements Filter{//继承过滤器类，重写方法,在web.xml中配置

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resq,
			FilterChain chain) throws IOException, ServletException {
		//强转
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resq;
		//处理业务
		//1.从session中获取用户对象 2.判断是否为空 3.判断权限是否是管理员
		User user = (User) request.getSession().getAttribute("user");
		//判断user权限
		if (user!=null) {
			if (!user.getRole().equals("admin")) {
				response.getWriter().write("普通用户无权限，无法访问！");
				response.setHeader("refresh", "2;url="+request.getContextPath()+"/index.jsp");//两秒跳转
				return;//终止程序
			}
		chain.doFilter(request, response);
		}
		response.sendRedirect(request.getContextPath()+"/login.jsp");
		//放行
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	
}
