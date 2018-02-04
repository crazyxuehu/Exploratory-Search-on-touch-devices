package UnitTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.service.ExploreService;
import com.graduate.server.service.SearchService;

public class TestExploreService extends BaseTest{
	@Autowired SearchService service;
	@Autowired ExploreService exservice;
	
	@Test
	public void testSimResult() {
		Scanner sc =new Scanner(System.in);
		System.out.println("input the search entity and end with -1");
		List<String>list = new ArrayList<String>();
		List<Entity>entityList = null;
		//while(true) {
			String name = sc.nextLine();
			//if(name.equals("-1")) break;
			//else 
			list.add(name);
		//}
		System.out.println(list.size());
		if(service==null)System.out.println("Yes");
		if(list.size()>0) {
			entityList=service.getQueryEntity(list);
			List<Entity>simList=exservice.getSimEntity(entityList);
			for(Entity entity:simList) {
				System.out.println(entity.getName()+" "+entity.getScore());
			}
			List<Feature>simFeaturelist=service.getQueryFeature(simList);
			for(int i=0;i<simFeaturelist.size();i++){
				System.out.println(simFeaturelist.get(i).getTarget().getName()+" "+simFeaturelist.get(i).getScore()+" "+simFeaturelist.get(i).getRelation().getDirection());
			}
		}
		
	}
}
