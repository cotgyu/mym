# mym

--- 


-	커밋 로그 구분해서 알아보기 쉽게하기

	```text
	feat (feature)
	fix (bug fix)
	docs (documentation)
	style (formatting, missing semi colons, …)
	refactor
	test (when adding missing tests)
	chore (maintain)
	```

-	gradle 설정 추가한 것 (타임리프, devtools)

	```text
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	```

-	application.yml 추가한 것

	-	html 바로 반영 옵션

		-	thymeleaf: cache: false

	-	리소스가 바뀌면 브라우저를 자동으로 새로고침해주는 트리거

		-	파일을 수정하고, 빌드만 하면 브라우저에서 자동으로 새로고침도 해줌

		-	devtools: livereload: enabled: true

-	clone 후 인텔리제이에서 설정한 것 메모

	-	인텔리제이 오른쪽 하단 import module from gradle

	-	gradle 설정 팝업 뜸

		-	use auto-import 체크
		-	use gradle 'wrapper' task configuration 체크
			-	gradle-wrapper.properties 에 명시된 버전으로 설정되는 듯 싶음

-	프로젝트 실행 전 해야할 것

	-	인텔리제이 : Annotation Processors - Enable annotation processing 체크

	-	gradle 탭 - tasks - other - compileQuerydsl 더블클릭

		-	querydsl 실행에 필요함
		-	entity 추가 후 querydsl 사용해야할 때 실행 필

	-	h2 설정

		-	h2 생성 참고 https://www.inflearn.com/questions/22054
		-	./h2.sh 실행 (처음에 실행하면 admin 권한으로 디비생성가능)
		-	jdbc:h2:~/mym , jdbc:h2:~/mym-test 를 사용해 mym, mym-test 생성
		-	이후 h2 웹에서 jdbc:h2:tcp://localhost/~/mym 으로 접속

	-	Run/Debug Configurations

		-	On update action: Update classes and resources 설정
		-	On frame deactivation : Update classes and resources 설정

-	프로젝트 테스트

	-	src/text/java/com/mym -> mym 오른쪽클릭 -> debug 'Texts in 'com.mym" 실행
	-	pass 뜨는지 확인

-	프로젝트 기동

	-	MymApplication.java -> 왼쪽 실행 or Run 탭에서 실행
	-	http://localhost:8080/board/list/1
		-	게시판 (1: 페이지 번호)
	-	http://localhost:8080/board/detail/4
		-	게시판 상세 조회 (4: 게시물 번호)
	-	http://localhost:8080
		-	로그인 (계정정보 : testMember/pass , testAdmin/pass )
	-	http://localhost:8080/member/list/

		-	이 페이지는 권한 필요함, testAdmin/pass 로 로그인 후 다시 접속 확인

	-	나머지 rest api 는 postman 등으로 실행 가능

		-	test 코드에도 있음
