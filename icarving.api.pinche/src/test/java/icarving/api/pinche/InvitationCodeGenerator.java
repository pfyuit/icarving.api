package icarving.api.pinche;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class InvitationCodeGenerator {
	private static final int INVITATION_CODE_NUMBER = 60;
	private static Map<Integer, Integer> cache = new HashMap<>();

	public static void main(String[] args) {
		int count = 0;
		while (count < INVITATION_CODE_NUMBER) {
			int invitationCode = new Random().nextInt(1000000);
			if (invitationCode < 100000) {
				continue;
			}
			if (cache.get(invitationCode) != null) {
				continue;
			}
			cache.put(invitationCode, invitationCode);
			String sql = "INSERT INTO `icarving_pinche`.`invitation` (`creator_id`,`invitation_code`,`create_time`,`last_modify`,`is_used`)VALUE(0," + invitationCode
					+ ",'2015-01-21 01:00:00','2015-01-21 01:00:00',0);";
			System.out.println(sql);
			count++;
		}
		System.out.println("Total: " + cache.size());

	}

}
