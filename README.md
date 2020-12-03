# config

- 인증 요청
http://localhost:8081/oauth/authorize?client_id=testClientId&redirect_uri=http://localhost:8081/oauth2/callback&response_type=code&scope=read
http://localhost:8081/oauth/authorize?client_id=testClientId2&redirect_uri=http://localhost:8083/oauth2/callback&response_type=code&scope=read
- DB insert
- testClientId:testSecret
insert into oauth_client_details(client_id, resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri,authorities,access_token_validity,refresh_token_validity,additional_information,autoapprove)
values('testClientId',null,'{bcrypt}$2a$10$Vc/.wYqkai/Ln0qDi4y0O.5hflW.yDdMuV0IWJCMvXMiMV4SOgdAS','read,write','authorization_code,refresh_token','http://localhost:8081/oauth2/callback','ROLE_USER',36000,50000,null,null);
---
- testClientId2:testSecret2
insert into oauth_client_details(client_id, resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri,authorities,access_token_validity,refresh_token_validity,additional_information,autoapprove)
values('testClientId2',null,'{bcrypt}$2a$10$bU8LBnfD8UV.VWSfeAaVQOqems6SPZjHzmphnQGl8aPweZVEgHKue','read,write','authorization_code,refresh_token','http://localhost:8083/oauth2/callback','ROLE_USER',36000,50000,null,null);
---
- testClientId3:testSecret3
insert into oauth_client_details(client_id, resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri,authorities,access_token_validity,refresh_token_validity,additional_information,autoapprove)
values('testClientId3',null,'{bcrypt}$2a$10$8R7U2jebuHEQpcKl4SA7U.jYrSsjI.avsIj2m92SGsOFXOfIaAj2O','read,write','authorization_code,refresh_token','http://localhost:8080/oauth2/callback','ROLE_USER',36000,50000,null,null);
---
{bcrypt} 부분은 testSecret을 암호화 한 것으로, test code 를 구동시켜 출력한 것임.


유저 인증 테스트 코드를 돌린 후 유저 정보로 로그인 가능하도록 구현.

- 재발급 토큰 생성
http://localhost:8081/oauth2/token/refresh?refreshToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJka3lvdTdAbmF2ZXIuY29tIiwic2NvcGUiOlsicmVhZCJdLCJhdGkiOiI2ODgyYzMxMy1hZjM0LTRiNTUtYjM5Zi03ODA5ZGNkNDI3MmMiLCJleHAiOjE2MDY4NTE3OTMsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIyZDIzYWE2OS1jYTUwLTQwNWUtYWYxMC0zMTQwNzg3YzVjY2YiLCJjbGllbnRfaWQiOiJ0ZXN0Q2xpZW50SWQifQ.OvzyYJJR7vaiQoI0QQLUf0PSIVVqtluXG8oeeVTIbVc


- todo
하나의 인증 서버에 두개의 리소스가 접근하고자 할 때, 동적으로 yml 파일을 설정할 수 있어야 한다.
https://myeongjae.kim/blog/2020/02/01/spring-oauth-2-resource-server-handle-multiple-auth-server-tokens