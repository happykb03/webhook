package com.springcloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RefreshScope
public class Controller {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private Map<String, PFList> pfList;
	private int iListId = 1;
	
	@PostConstruct
	private void init() {   
		pfList = new HashMap<>();
		
		PFList user = PFList.builder()
				.userId("user"+iListId)
				.userName("홍길동")
				.listId(""+iListId++)
				.listSubject("홍길동의 포트폴리")
				.listContents("포폴 포폴 나 잘해?")
				.regTime("2021-05-11")
				.build();
		pfList.put(user.getUserId(), user);

		user = PFList.builder()
				.userId("user"+iListId)
				.userName("갑빠")
				.listId(""+iListId++)
				.listSubject("갑빠의 포트폴리")
				.listContents("갑빠 포폴 포폴 나 잘해?")
				.regTime("2021-05-13")
				.build();
		pfList.put(user.getUserId(), user);
		
		user = PFList.builder()
				.userId("user"+iListId)
				.userName("백호")
				.listId(""+iListId++)
				.listSubject("백호의 포트폴리")
				.listContents("백호 포폴 포폴 나 잘해?")
				.regTime("2021-05-14")
				.build();
		pfList.put(user.getUserId(), user);
		
	}
	
    @Value("${greeting:Hi}")
    private String greeting;
   
    @GetMapping("/greeting/{message}")
    @ApiOperation(value="Test Spring cloud bus")
    public String echo(@PathVariable String message) {
    	log.info("### Received: webhook > /greeting/"+message);
    	log.info("### Sent: "+greeting + "=>" + message);
        return greeting + " => " + message;
    } 
    
    @GetMapping("getPfList")
	@ApiOperation(value="사용자 정보 목록", notes="사용자 정보 목록을 반환합니다. ")
	public ResponseEntity<Result<List<PFList>>> getPfList() {
		List<PFList> lists = pfList
				.values()
				.stream()
				.collect(Collectors.toCollection(ArrayList::new));
		
		return new ResponseEntity<Result<List<PFList>>>(Result.<List<PFList>>builder()
				.returnCode(true)
				.result(lists)
				.build(), HttpStatus.OK);
		
	}
    
}
