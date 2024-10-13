package xenhire.constants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class AppConstants {

	public final static String SUCCESS = "success";
	public final static String FAILURE = "failure";
	public final static String NOT_FOUND = "not found";
	public final static String USER_ALREADY_EXISTS = "user already exists";
	public final static String RECORD_NOT_EXISTS = "record not exists";
	public final static String RECORD_ALREADY_EXISTS = "record already exists";
	public final static String FOUND = "found";
	public final static String SAVED = "saved";
	public final static String UPDATED = "updated";
	public final static String NOTIFICATION_SENT = "notification sent";
}

