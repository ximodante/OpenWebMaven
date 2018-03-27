package openadmin.util.edu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtilsEdu {

	public static String getDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:nnnnnnnnn");
		return LocalDateTime.now().format(formatter);
	}

	public static void main(String[] args) {
		System.out.println(getDateTime());
	}
}
