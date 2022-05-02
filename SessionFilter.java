@WebFilter(urlPatterns = {"/control/filter/*"})
public class SessionFilter implements Filter {
    @Override
    public void destroy() {
        System.out.println("filter destroy");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servlet = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //取出session里面的name属性,如果name为空, 就重定向到index界面
        String name = (String) servlet.getSession().getAttribute("name");
        if (Objects.isNull(name)){
            response.sendRedirect("/control/index");
            return;
        }
        //name属性存在, 即会话没有过期, 那么允许本次请求
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
