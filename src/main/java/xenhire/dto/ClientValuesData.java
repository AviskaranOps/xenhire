package xenhire.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.request.ClientValuesRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientValuesData {
	
	int rank;
	String valueName;
	int staticScore;
	int dynamicScore;

}
