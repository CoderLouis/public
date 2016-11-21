package core;

import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Search {
	private String xuexiao;
	private String xiaoqu;
	private String max;
	private String min;
	private String chaoxiang;
	private String banta;
	private String schoolFile;
	private static final String BASE_URL = "http://bj.lianjia.com/ershoufang/";
	private static final Logger logger = Logger.getLogger(Search.class.getName());
	
	
	public String getSchoolFile() {
		return schoolFile;
	}
	public void setSchoolFile(String schoolFile) {
		this.schoolFile = schoolFile;
	}
	public String getXuexiao() {
		return xuexiao;
	}
	public void setXuexiao(String xuexiao) {
		this.xuexiao = xuexiao;
	}
	public String getXiaoqu() {
		return xiaoqu;
	}
	public void setXiaoqu(String xiaoqu) {
		this.xiaoqu = xiaoqu;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getChaoxiang() {
		return chaoxiang;
	}
	public void setChaoxiang(String chaoxiang) {
		this.chaoxiang = chaoxiang;
	}
	public String getBanta() {
		return banta;
	}
	public void setBanta(String banta) {
		this.banta = banta;
	}
	
	public List<FangyuanEntity> doSearch() throws Exception{
		List<FangyuanEntity> list = new LinkedList<FangyuanEntity>();
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/users/zhenlei/dev/jars/selenium/phantomjs");
		PhantomJSDriver webDriver = new PhantomJSDriver(dc);
		
		webDriver.get(Search.BASE_URL);
		
		for(String line : schoolFile.replaceAll("\r", "").split("\n")){
			if(line.contains("---")){
				String[] infos = line.split("---");
				String xiaoqu = infos[0];
				logger.info("处理 " + xiaoqu + "....");
				list.addAll(getFangyuan(webDriver, xiaoqu));
			}
		}
		
		webDriver.quit();
		
		return list;
	}
	
	public List<FangyuanEntity> getFangyuan(WebDriver webDriver, String xiaoqu){
		List<FangyuanEntity> list = new LinkedList<FangyuanEntity>();
		//input xiaoqu
		WebElement input = webDriver.findElement(By.id("searchInput"));
		input.clear();
		input.sendKeys(xiaoqu);

		webDriver.findElement(By.cssSelector("button[class='searchButton']")).click();
		//go through all the fangyuan
		WebElement title, address, flood, priceInfo, followInfo;
		try {
			WebElement parentElement = webDriver.findElement(By.className("sellListContent"));
			for(WebElement infoClear : parentElement.findElements(By.cssSelector("li[class='clear']"))){
				FangyuanEntity entity = new FangyuanEntity();
				list.add(entity);
				
				title = infoClear.findElement(By.className("title"));
				logger.info(title.getText());
				address = infoClear.findElement(By.className("address"));
				flood = infoClear.findElement(By.className("flood"));
				priceInfo = infoClear.findElement(By.className("priceInfo"));
				followInfo = infoClear.findElement(By.className("followInfo"));
				
				entity.setTitle(title.getText());
				entity.setLink(title.findElement(By.tagName("a")).getAttribute("href"));
				entity.setFloor(flood.getText());
				entity.setAddress(address.getText());
				entity.setTotalPrice(priceInfo.findElement(By.className("totalPrice")).getText());
				entity.setUnitPrice(priceInfo.findElement(By.className("unitPrice")).getText());
				entity.setFollowInfo(followInfo.getText());
			}
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			
			logger.warning(xiaoqu + " 没有房源。");
		}
		
		return list;
	}
}

