import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ThreadTime extends Thread {

	private Control cont = new Control();
	private DAO dao = new DAO();
	private ArrayList<OrderList> ol = null;
	private ArrayList<FinalVO> fvoList = null;
	
	private SimpleDateFormat transFormat = null;
	
	private int hour, min;
	
	Calendar cal = null;
	private int state = 0;
	private int state1 = 0;

	public void run() {
		// 5�� ������ �� �ð��� üũ
		
		while (true) {
			cont.timeChecker();
			
			hour = cont.getHour();
			min = cont.getMin();
			
			// 12�ð� �ǰ� �ٲ� ����� ���ٸ� ����Ǵ� ����
	         if (hour == 12 && state == 0) {
	            ol = dao.getOrderArrayList();
	            cont.martSelect(ol);
	            state = 1;
	         }
	         
	         // 12�� 1���� �Ǹ� finalVO �� ��� �޼ҵ� ȣ��
	         if(hour == 12 && min == 1 && state1 == 0) {
	            this.fvoList = dao.allSave();
	         }

	         // �ٲ� ����� �ְ� 12�ð� �ƴϸ� �ٲ۱���� �ʱ�ȭ
	         if ((state == 1 && state1 == 1) && hour != 12) {
	            state1 = 0;
	            state = 0;
	            dao.resetState();
	         }
	         
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				return;
			}
		}

	}

}
