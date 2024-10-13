package xenhire.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginatedResponse {

	int pageNo;
	int pageSize;
	Object data;
	long totalCount;
	String result;
	String message;

}
