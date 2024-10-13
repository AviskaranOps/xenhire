package xenhire.request;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientValuesRequest {
	
	List<ValuesRating> ratingList;
	String templateName;
	String templateTag;
	String templateDescription;

}
