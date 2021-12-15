package camp.test;

import com.campArea.model.CampAreaDAOImpl;
public class CampAreaDAOtest {

	public static void main(String[] args) {

		CampAreaDAOImpl cad = new CampAreaDAOImpl();

		// 查全部資料
//		List<CampAreaVO> caList = cad.getAll();
//		for (CampAreaVO av : caList) {
//			System.out.println(av.toString());
//		}

		// 新增
//		CampAreaVO camparea = new CampAreaVO();
//		camparea.setCampId(1);
//		camparea.setCampAreaName("5區");
//		camparea.setCampAreaMax(5);
//		camparea.setWeekdayPrice(1000);
//		camparea.setHolidayPrice(1300);
//		camparea.setCapitationMax(2);
//		camparea.setPerCapitationFee(200);
//		cad.insert(camparea);
		System.out.println("新增成功");
		
		//修改
//		CampAreaVO camparea2 = new CampAreaVO();
//		camparea2.setCampId(1);
//		camparea2.setCampAreaName("4區");
//		camparea2.setCampAreaMax(6);
//		camparea2.setWeekdayPrice(2000);
//		camparea2.setHolidayPrice(1000);
//		camparea2.setCapitationMax(3);
//		camparea2.setPerCapitationFee(100);
//		camparea2.setCampAreaId(16);
//		cad.update(camparea2);
//		System.out.println("修改成功");

		//刪除
		cad.delete(23);
		System.out.println("刪除成功");

	}
}

