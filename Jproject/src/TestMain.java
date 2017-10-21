import java.util.ArrayList;
import java.util.Vector;

public class TestMain {

	public static void main(String[] args) {
		ThreadTime tt = new ThreadTime();
		tt.start();
		DAO dao = new DAO();
		dao.allSave();
		ArrayList<FinalVO> fvoList = dao.getFVOList();
		FinalVO fvo = new FinalVO();
		
		
	}
	

}
