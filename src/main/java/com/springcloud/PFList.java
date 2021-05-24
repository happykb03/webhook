package com.springcloud;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PFList {
	private String userId;
	private String userName;
	private String listId;
	private String listSubject;
	private String listContents;
	private String regTime;
}
