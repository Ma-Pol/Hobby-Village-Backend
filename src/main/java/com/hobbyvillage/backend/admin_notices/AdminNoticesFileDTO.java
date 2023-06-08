package com.hobbyvillage.backend.admin_notices;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminNoticesFileDTO {
	private Integer notFileCode;
	private String notFileOriName;
	private String notFileSavName;
}
