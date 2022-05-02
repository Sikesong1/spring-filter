@WebFilter(urlPatterns = {"/control/filter/*"})
public class SessionFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servlet = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //取出session里面的name属性,如果name为空, 就重定向到index界面
        
        String[] urls = new String[]{
            "/login",
            "/logout"
        };
        
        boolean check = check(urls, requestURI);
        
        if(check){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        
        String name = (String) servlet.getSession().getAttribute("name");
        if (Objects.isNull(name)){
            response.sendRedirect("/control/index");
            return;
        }
        
        //name属性存在, 即会话没有过期, 那么允许本次请求
        filterChain.doFilter(servletRequest, servletResponse);
    }
    
    public boolean check(String[] urls , String requestURI){
        for(Sring url : urls){
            boolean match = PATH_MATCHER.match(url, requestURI)
            if(match){
                return true;
            }
        }
        
        return false;
    }
}
