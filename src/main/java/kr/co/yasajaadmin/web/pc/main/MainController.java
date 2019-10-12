package kr.co.yasajaadmin.web.pc.main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.yasajaadmin.service.TestService;


@Controller
public class MainController {

	@Autowired
	BasicDataSource dataSource;
	
	@Autowired 
	TestService testService;
	
	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("mapper",testService.selectNow());
		return "/pc/main/home";
	}
	
	@GetMapping("/dbTest.do")
    public String dbTest(Model model) {
        Connection conn = null;
        Statement st = null;
        
        try {
            conn = dataSource.getConnection();
            st = conn.createStatement();
            ResultSet rs = st.executeQuery("select now() as now;");
            
            while(rs.next()) {
                model.addAttribute("serverTime", rs.getString("now"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();    
            
        } finally {
            try {
                if(st != null) st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            try {
                if(conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }                        
        }
        
        return "/pc/main/dbtest";
    }
	
	
}

