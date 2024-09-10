package BeanDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import BeanDao.BaseDao;
import Bean.Product;


public class ProductDao {
	
	/*����Ա���û�������Ʒ��Ϣ*/ 
	public static ArrayList<Product> selectAll(int cpage,int count){
		ArrayList<Product> list = new ArrayList<Product>();
		ResultSet rs = null;
		Connection conn = BaseDao.getconn();
		PreparedStatement ps = null; 
		try {
			ps = conn.prepareStatement("select * from product limit ?, ?");
			ps.setInt(1, count*(cpage-1));
			ps.setInt(2, count);
			rs = ps.executeQuery();
			while(rs.next()){
				Product pro = new Product(rs.getInt("proid"),
						rs.getString("proname"), 
						rs.getString("prodetail"),
						rs.getInt("proprice"),
						rs.getInt("prostock"),
						rs.getInt("cateid"),
						rs.getInt("catebigid"),
						rs.getString("propname"));
				list.add(pro);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			BaseDao.closeall(rs, ps, conn);
		}
		return list;
	}
	
	
	/*��ҳ*/ 
	public static int totalPage(int count){
		int tpage=1;
		Connection conn=BaseDao.getconn();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement("select count(*) from product");
			rs=ps.executeQuery();
			while(rs.next()){
				int sum=rs.getInt(1);
				if(sum%count==0){
					tpage=sum/count;
				}else{
					tpage=sum/count+1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			BaseDao.closeall(rs, ps, conn);
		}
		return tpage;
	}
	/*ɾ����Ʒ*/ 
	public static int deleteProduct(int proid){
		String sql = "delete from product where proid=?";
		Object[] params = {proid};
		return BaseDao.exectuIUD(sql, params);
	}
	
	/*������Ʒ*/
	public static int insert(Product pro){
		String sql = "insert into product values(null,?,?,?,?,?,?,?)";
		Object[] params = {pro.getProname(),pro.getProdetail(),pro.getProprice(),
				pro.getProstock(),pro.getCateid(),pro.getCatebigid(),pro.getPropname()};
		return BaseDao.exectuIUD(sql, params);
	}
	/*�û�������Ʒ����Ŀ*/
	public static ArrayList<Product> selectAllByBigid(int cpage,int count,int id){
		ArrayList<Product> list = new ArrayList<Product>();
		Connection conn =BaseDao.getconn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from product where catebigid= ? order by proid desc limit ?,?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, count*(cpage-1));
			ps.setInt(3, count);
			rs = ps.executeQuery();
			while(rs.next()) {
				Product p= new Product(rs.getInt("proid"),
						rs.getString("proname"), 
						rs.getString("prodetail"),
						rs.getInt("proprice"),
						rs.getInt("prostock"),
						rs.getInt("cateid"),
						rs.getInt("catebigid"),
						rs.getString("propname"));
				list.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BaseDao.closeall(rs, ps, conn);
		}
		
		return list;
	}
	/*��Ʒ����Ŀ��ҳ*/
	public static int totalPageByBigid(int count,int id){
		int tpage = 1;
		Connection conn = BaseDao.getconn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select count(*) from product where catebigid=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				int sum = rs.getInt(1);//��ñ��������
				if(sum%count==0){
					tpage = sum/count;//��������ÿҳ������������
				}else {
					tpage = sum/count+1;//����������Ҫ��һ
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BaseDao.closeall(rs, ps, conn);
		}
		return tpage;
	}
	/*�û�������Ʒ����Ŀ*/
	public static ArrayList<Product> selectAllByCid(int cpage,int count,int id){
		ArrayList<Product> list = new ArrayList<Product>();
		Connection conn =BaseDao.getconn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from product where cateid= ? order by proid desc limit ?,?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, count*(cpage-1));
			ps.setInt(3, count);
			rs = ps.executeQuery();
			while(rs.next()) {
				Product p= new Product(rs.getInt("proid"),
						rs.getString("proname"), 
						rs.getString("prodetail"),
						rs.getInt("proprice"),
						rs.getInt("prostock"),
						rs.getInt("cateid"),
						rs.getInt("catebigid"),
						rs.getString("propname"));
				list.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BaseDao.closeall(rs, ps, conn);
		}
		
		return list;
	}
	/*��Ʒ����Ŀ��ҳ*/
	public static int totalPageByCid(int count,int id){
		int tpage = 1;
		Connection conn = BaseDao.getconn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select count(*) from product where cateid=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				int sum = rs.getInt(1);//��ñ��������
				if(sum%count==0){
					tpage = sum/count;//��������ÿҳ������������
				}else {
					tpage = sum/count+1;//����������Ҫ��һ
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BaseDao.closeall(rs, ps, conn);
		}
		return tpage;
	}
	/*����Ա���û�������Ʒ*/
	public static Product selectById(int id){
		Product pro=null;
		ResultSet rs = null;
		Connection conn = BaseDao.getconn();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("select * from product where proid=? ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				pro= new Product(rs.getInt("proid"),
						rs.getString("proname"), 
						rs.getString("prodetail"),
						rs.getInt("proprice"),
						rs.getInt("prostock"),
						rs.getInt("cateid"),
						rs.getInt("catebigid"),
						rs.getString("propname"));
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			BaseDao.closeall(rs, ps, conn);
		}
		return pro;
	}
	/*����Ա���û�������Ʒ*/
	public static Product selectByPComment(int id){
		Product pro=null;
		ResultSet rs = null;
		Connection conn = BaseDao.getconn();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("select * from product,comment,ordertable where product.proid=? and comment.otid=ordertable.otid and ordertable.proid=product.proid order by coid desc limit 0,1;");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				pro= new Product(rs.getInt("proid"),
						rs.getString("proname"), 
						rs.getString("prodetail"),
						rs.getInt("proprice"),
						rs.getInt("prostock"),
						rs.getInt("cateid"),
						rs.getInt("catebigid"),
						rs.getString("propname"),
						rs.getString("codetail"));
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			BaseDao.closeall(rs, ps, conn);
		}
		return pro;
	}
	/*����Ա�޸���Ʒ*/
	public static int update(Product pro){
		String sql = "update product set proname=?,prodetail=?,proprice=?,prostock=?,cateid=?,catebigid=?,propname=? where proid=?";
		Object[] params = {pro.getProname(),pro.getProdetail(),pro.getProprice(),
				pro.getProstock(),pro.getCateid(),pro.getCatebigid(),pro.getPropname(),pro.getProid()};
		return BaseDao.exectuIUD(sql, params);
	}
	/*������Ʒ*/
	public static ArrayList<Product> selectAllByName(int cpage,int count,String selectname){
		ArrayList<Product> list = new ArrayList<Product>();
		ResultSet rs = null;
		Connection conn = BaseDao.getconn();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("select * from product where prodetail like ? order by proid desc limit ?,?");
			ps.setString(1, "%"+selectname+"%");
			ps.setInt(2, count*(cpage-1));
			ps.setInt(3, count);
			rs = ps.executeQuery();
			while(rs.next()){
				Product pro = new Product(rs.getInt("proid"),
						rs.getString("proname"), 
						rs.getString("prodetail"),
						rs.getInt("proprice"),
						rs.getInt("prostock"),
						rs.getInt("cateid"),
						rs.getInt("catebigid"),
						rs.getString("propname"));
				list.add(pro);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			BaseDao.closeall(rs, ps, conn);
		}
		return list;
	}
	/*��ѯ��ҳ*/
	public static int totalPageByName(int count,String selectname){
		int tpage = 1;
		Connection conn = BaseDao.getconn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select count(*) from product where prodetail like ?");
			ps.setString(1, "%"+selectname+"%");
			rs = ps.executeQuery();
			if(rs.next()){
				int sum = rs.getInt(1);//��ñ��������
				if(sum%count==0){
					tpage = sum/count;//��������ÿҳ������������
				}else {
					tpage = sum/count+1;//����������Ҫ��һ
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BaseDao.closeall(rs, ps, conn);
		}
		return tpage;
	}
	/*������Ʒ*/
	public static ArrayList<Product> selectAllByHot(){
		ArrayList<Product> list = new ArrayList<Product>();
		Connection conn =BaseDao.getconn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select ordertable.proid,propname,sum(ordertable.procount) as sales from ordertable,product where ordertable.proid=product.proid group by proid order by sales desc limit 0,8;";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Product pro= new Product(rs.getInt("proid"),rs.getString("propname"));
				list.add(pro);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BaseDao.closeall(rs, ps, conn);
		}
		
		return list;
	}
	/*�ͼ���Ʒ*/
	public static ArrayList<Product> selectAllByLow(){
		ArrayList<Product> list = new ArrayList<Product>();
		Connection conn =BaseDao.getconn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select *from product order by proprice limit 0,4;";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Product pro = new Product(rs.getInt("proid"),
						rs.getString("proname"), 
						rs.getString("prodetail"),
						rs.getInt("proprice"),
						rs.getInt("prostock"),
						rs.getInt("cateid"),
						rs.getInt("catebigid"),
						rs.getString("propname"));
				list.add(pro);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BaseDao.closeall(rs, ps, conn);
		}
		
		return list;
	}

}
