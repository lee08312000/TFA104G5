package camp.test;

import com.camp.model.CampDAOImpl;

public class CampDAOtest {

	public static void main(String[] args) {

		CampDAOImpl cad = new CampDAOImpl();

		// 查全部資料
//		List<CampVO> caList = cad.getAll();
//		for (CampVO av : caList) {
//			System.out.println(av.toString());
//		}

		// 新增
//		CampVO camp = new CampVO();		
//		camp.setCompanyId(1);
//		camp.setCampStatus(1);
//		camp.setCampName("艾莉絲2");
//		camp.setCampRule("不可以放煙火i");
//		camp.setCampPic2(null);
//		camp.setCampPic3(null);
//		camp.setCampPic4(null);
//		camp.setCampPic5(null);
//		camp.setCampAddress("新竹市香山區麗山街112號");
//		camp.setCampPhone("088155936");
//		camp.setCertificateNum("103245");
//		camp.setCertificatePic(null);
//		java.util.Date starttijd = new java.util.Date(System.currentTimeMillis());
//		camp.setCampLaunchedTime(new Timestamp(starttijd.getTime()));
//		camp.setCampAppliedLaunchTime(new Timestamp(starttijd.getTime()));
//		camp.setLongitude(new Double("120.1000455"));
//		camp.setLattitude(new Double("120.100123"));
//		camp.setCampDiscription("好用營地dd");
//		cad.insert(camp);
//		System.out.println("新增成功");
		

//		// 修改
//		CampVO camp1 = new CampVO();		
//		camp1.setCompanyId(1);
//		camp1.setCampStatus(1);
//		camp1.setCampName("艾莉絲2");
//		camp1.setCampRule("不可以放煙火i");
//		camp1.setCampPic2(null);
//		camp1.setCampPic3(null);
//		camp1.setCampPic4(null);
//		camp1.setCampPic5(null);
//		camp1.setCampAddress("新竹市香山區麗山街111號");
//		camp1.setCampPhone("088155935");
//		camp1.setCertificateNum("103245");
//		camp1.setCertificatePic(null);
//		java.util.Date starttijd = new java.util.Date(System.currentTimeMillis());
//		camp1.setCampLaunchedTime(new Timestamp(starttijd.getTime()));
//		camp1.setCampAppliedLaunchTime(new Timestamp(starttijd.getTime()));
//		camp1.setLongitude(new Double("120.1000455"));
//		camp1.setLattitude(new Double("120.100123"));
//		camp1.setCampDiscription("好用營地d");
//		camp1.setCampId(5);
//		cad.update(camp1);
//		System.out.println("修改成功");

		// 刪除
		cad.delete(9);
		System.out.println("刪除成功");

	}

}
