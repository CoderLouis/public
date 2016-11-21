package servlet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;

import core.FangyuanEntity;
import core.SchoolEntity;
import core.Search;



/**
 * Servlet implementation class Xuexiao
 */
@WebServlet("/servlet/Xuexiao")
public class Xuexiao extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(Xuexiao.class.getName());
	private EntityManagerFactory factory;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Xuexiao() {
        super();
        // TODO Auto-generated constructor stub
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		ObjectMapper mapper = new ObjectMapper();
		factory = (EntityManagerFactory) this.getServletContext().getAttribute("factory");
		try {
			if("list".equalsIgnoreCase(action)){
				mapper.writeValue(out, getXuexiaoList());
			}
			else if("search".equalsIgnoreCase(action)){
				mapper.writeValue(out, search(request));
			}
			else if("add".equalsIgnoreCase(action)){
				add(request);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.setStatus(500);
			out.println(e.getMessage());
			logger.severe(e.getMessage());;
		}
	}
	
	private List<SchoolEntity> getXuexiaoList() throws Exception{
		EntityManager manager = factory.createEntityManager();
		List<SchoolEntity> list = manager.createQuery("SELECT schoolEntity FROM SchoolEntity schoolEntity ORDER BY schoolEntity.name").getResultList();
		manager.close();
		return list;
	}
	
	private List<FangyuanEntity> search(HttpServletRequest request) throws Exception{
		EntityManager manager = factory.createEntityManager();
		SchoolEntity entity = manager.find(SchoolEntity.class, request.getParameter("id"));
		manager.close();
		Search search = new Search();
		search.setSchoolFile(entity.getXiaoqu());
		List<FangyuanEntity> list = search.doSearch();
		if("totalPrice".equalsIgnoreCase(request.getParameter("sort")) && "desc".equalsIgnoreCase(request.getParameter("order"))){	
			Collections.sort(list, new Comparator<FangyuanEntity>(){

				@Override
				public int compare(FangyuanEntity o1, FangyuanEntity o2) {
					// TODO Auto-generated method stub
					return Integer.valueOf(o2.getTotalPrice().substring(0, o2.getTotalPrice().length() - 1)).compareTo(Integer.valueOf(o1.getTotalPrice().substring(0, o1.getTotalPrice().length() - 1)));
				}
					
			});
		}
		else{
			Collections.sort(list, new Comparator<FangyuanEntity>(){

				@Override
				public int compare(FangyuanEntity o1, FangyuanEntity o2) {
					// TODO Auto-generated method stub
					return Integer.valueOf(o1.getTotalPrice().substring(0, o1.getTotalPrice().length() - 1)).compareTo(Integer.valueOf(o2.getTotalPrice().substring(0, o2.getTotalPrice().length() - 1)));
				}
				
			});
		}
		
		return list;
	}
	
	private void add(HttpServletRequest request) throws Exception{
		String schoolName = request.getParameter("schoolName");
		String chuzhong = request.getParameter("chuzhong");
		String xiaoqu = request.getParameter("xiaoqu");
		
		EntityManager manager = factory.createEntityManager();
		List list = manager.createQuery("SELECT schoolEntity FROM SchoolEntity schoolEntity WHERE schoolEntity.name='" + schoolName + "'").getResultList();
		manager.close();
		if(list.size() > 0){
			throw new Exception(schoolName + " 已经存在了。");
		}
		
		manager = factory.createEntityManager();
		manager.getTransaction().begin();
		SchoolEntity entity = new SchoolEntity();
		entity.setName(schoolName);
		entity.setChuzhong(chuzhong);
		entity.setXiaoqu(xiaoqu);
		manager.persist(entity);
		manager.getTransaction().commit();
	}

}

