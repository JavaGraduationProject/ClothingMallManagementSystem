package Bean;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;

import BeanDao.BaseDao;
import BeanDao.CategoryDao;
import Bean.Category;

public class Chart {
    public String cname;
	public int csum;
	
	
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public int getCsum() {
		return csum;
	}


	public void setCsum(int csum) {
		this.csum = csum;
	}
 
	public Chart(String acname,int acsum){
		cname=acname;
		csum=acsum;
		
	}

	public static String generateBarChart(HttpSession session) throws IOException {

		/**
		 * ���´���ͨ������������ʽ��Q������������
		 */
		// ����������ʽ
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// ���ñ�������
		standardChartTheme.setExtraLargeFont(new Font("����", Font.PLAIN,25));
		// ����ͼ��������
		standardChartTheme.setRegularFont(new Font("����", Font.PLAIN, 20));
		// �������������
		standardChartTheme.setLargeFont(new Font("����", Font.PLAIN, 20));
		// Ӧ��������ʽ
		ChartFactory.setChartTheme(standardChartTheme);

		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		ArrayList<Chart> list = new ArrayList<Chart>();
		ResultSet rs = null;
		Connection conn = BaseDao.getconn();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("select catename,sum(procount*ordertable.proprice) as sumvalue from ordertable,product,category where ordertable.proid=product.proid and product.catebigid=category.cateid group by catename");
			rs = ps.executeQuery();
			while(rs.next()){
				dataset.addValue(rs.getInt("sumvalue"),"ͯװ",rs.getString("catename"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			BaseDao.closeall(rs, ps, conn);
		}
		

		// ����Ĭ��������ݼ��ɼ�����
		
		 
		JFreeChart chart = ChartFactory.createBarChart3D("��վ��ʷ����ͳ��", 
		                  "",
		                  "������",
		                  dataset,
		                  PlotOrientation.VERTICAL,
		                  false,
		                  false,
		                  false);
		
		CategoryPlot plot = chart.getCategoryPlot();  
		//�������񱳾���ɫ
		plot.setBackgroundPaint(Color.white);
		//��������������ɫ
		plot.setDomainGridlinePaint(Color.pink);
		//�������������ɫ
		plot.setRangeGridlinePaint(Color.pink);
		
		 
		// ��ʾÿ��������ֵ�����޸���ֵ���������ԣ�renderer��Ⱦ����
		BarRenderer3D renderer = new BarRenderer3D();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBaseItemLabelFont(new Font("����",Font.PLAIN,15));
		

		renderer.setBasePositiveItemLabelPosition(
				new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(10D);
		
		// ����ƽ����֮��ļ��
		renderer.setItemMargin(0.4);
		//
		renderer.setMaximumBarWidth(0.15);
		// ����ʽӦ�õ�CategoryPlot
		plot.setRenderer(renderer);
		
	

		

		// ʹ��ServletUtilities�����������ɵ�ͼ����ΪpngͼƬ
		String fileName = ServletUtilities.saveChartAsPNG(chart, // ���������ɵ�ͼ��
				700, // Ҫ����ͼƬ���
				500, // Ҫ����ͼƬ�ĸ߶�
				session // HttpSession
		);

		// ����ͼƬ���ļ���
		return fileName;

	}

}
