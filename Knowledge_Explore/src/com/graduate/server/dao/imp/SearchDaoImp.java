package com.graduate.server.dao.imp;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.lob.DefaultLobHandler;

import com.graduate.server.basedao.BaseDao;
import com.graduate.server.dao.SearchDao;
import com.graduate.server.model.Entity;

@Repository
public class SearchDaoImp extends BaseDao implements SearchDao {

	@Override
	public String getPwd(String name) {
		StringBuffer sql=new StringBuffer();
		sql.append("select paassword from xuehu.seed");
		sql.append(" where name=?");
		return getJdbcTemplate().queryForObject(sql.toString(), String.class, new Object[]{name});
	}

	@Override
	public List getResult() {
		StringBuffer sql=new StringBuffer();
		sql.append(" select * from xuehu.result");
		return getJdbcTemplate().queryForList(sql.toString());
	}

	@Override
	public void loadData(List list) {
		StringBuffer sql=new StringBuffer();
		sql.append("insert into xuehu.RELATIONID values(?,?)");
		for(int i=0;i<list.size();i++){
			String content=(String) list.get(i);
			String[]arg=content.split("	");
			String entity=arg[0];
			int id1=Integer.parseInt(arg[1]);
			getJdbcTemplate().update(sql.toString(),new Object[]{entity,id1});
		}
	}

	@Override
	public void testLobHandler(final String entity,final String text) {
		final LobHandler lobHandler=new DefaultLobHandler();  
		String sql = "insert into knowledge.abstract values(?,?)";
		getJdbcTemplate().execute(sql,new AbstractLobCreatingPreparedStatementCallback(lobHandler){
		@Override
		protected void setValues(PreparedStatement ps, LobCreator lobCreator)
		throws SQLException, DataAccessException {
			ps.setString(1,entity);
					lobCreator.setClobAsString(ps,2,text);
				}
			});
		}

	@Override
	public void saveQuery(List<Entity> list) {
		for(Entity entity:list){
			if(entity!=null){
				StringBuffer  sql=new StringBuffer();
				sql.append("insert into knowledge.history(entityid,entityname,userid,time) values(?,?,' ',now())");
				System.out.println(sql.toString());
				try{
					getJdbcTemplate().update(sql.toString(),new Object[]{entity.getId(),entity.getName()});
					System.out.println("save entity "+entity.getName());
				}catch(Exception e){
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public List getPopular() {
		StringBuffer sql=new StringBuffer();
		sql.append("select entityname from(select count(*) as num,entityname from knowledge.history");
		sql.append(" group by entityname order by num desc) as myhistory limit 5");
		List<String>list=getJdbcTemplate().queryForList(sql.toString(),String.class);
		return list;
	}

	@Override
	public List getSearchAll(int num) {
		StringBuffer sql=new StringBuffer();
		sql.append("select entityname from(select count(*) as num,entityname from knowledge.history");
		sql.append(" group by entityname order by num desc) as myhistory limit "+num);
		List<String>list=getJdbcTemplate().queryForList(sql.toString(),String.class);
		return list;
	}

	@Override
	public void saveEntity(List<String> list) {
		for(String name:list){
			if(name!=null){
				StringBuffer  sql=new StringBuffer();
				sql.append("insert into xuehu.image(id,name) values(imgid.nextval,?)");
				try{
					getJdbcTemplate().update(sql.toString(),new Object[]{name});
				}catch(Exception e){
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public List<String> getImg() {
		StringBuffer sql=new StringBuffer();
		sql.append("select distinct name from knowledge.image");
		List<String>list=getJdbcTemplate().queryForList(sql.toString(),String.class);
		return list;
	}

	@Override
	public void updateImg(HashMap<String, String> mp) {
		int cnt=0;
		System.out.println("123");
		for(String name:mp.keySet()){
			StringBuffer sql=new StringBuffer();
			sql.append("insert into knowledge.abstract values(?,?)");
			try{
				cnt++;
				getJdbcTemplate().update(sql.toString(),new Object[]{name,mp.get(name)});
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println(cnt);
	}

	@Override
	public String geturl(String name) {
		StringBuffer sql=new StringBuffer();
		sql.append("select url from knowledge.image where name=?");
		List<String>list=getJdbcTemplate().queryForList(sql.toString(),String.class,new Object[]{name});
		if(list.size()>0){
			return list.get(0);
		}else return "./img/not-found.png";
	}

	@Override
	public String getAbstract(String query) {
		StringBuffer sql=new StringBuffer();
		sql.append("select detail from knowledge.abstract where entity=?");
		List<String>list=getJdbcTemplate().queryForList(sql.toString(),String.class,new Object[]{query});
		if(list.size()>0){
			return list.get(0);
		}else{
			return "there is no more information!";
		}
	}

	
}
