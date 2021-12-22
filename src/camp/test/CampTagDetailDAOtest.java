package camp.test;

import com.campTagDetail.model.CampTagDetailDAOlmpl;

public class CampTagDetailDAOtest {

	public static void main(String[] args) {

		CampTagDetailDAOlmpl cad = new CampTagDetailDAOlmpl();

		// 查全部資料
		// CampTagDetailVO ctvo = cad.findByPrimaryKey(1, 1);
		// System.out.println(ctvo.toString());

		// 新增

//		CampTagDetailVO camptagdetail = new CampTagDetailVO();
//		camptagdetail.setCampId(4);
//		camptagdetail.setCampTagId(14);
//		cad.insert(camptagdetail);
//		System.out.println("新增成功");

		// 修改

//		CampTagDetailVO camptagdetail1 = new CampTagDetailVO();
//		camptagdetail1.setCampIdNew(3);
//		camptagdetail1.setCampTagIdNew(14);
//		camptagdetail1.setCampId(4);
//		camptagdetail1.setCampTagId(14);		
//		cad.update(camptagdetail1);
//		System.out.println("修改成功");
//		

		// 刪除
		cad.delete(3, 14);
		System.out.println("刪除成功");
	}

}
