# 교수님과 학생과의 약속을 쉽게 잡게 해주는 어플
[2019년 충남대 아이디어 경진대회 장려상]

```
팀명 : KNOCKTALK

팀원 : adelakim5, KumJungMin, JungSuBin

역할 : adelakim5(리얼타임 데이터베이스, 인증, 알람기능 구현), KumJungMin(프론트구상도 및 스케쥴 보기 구현), JunSunBin(스케쥴 추가,삭제,확인 기능 구현)
```
## 0. 개발환경
- 리얼타임 데이터베이스
  
- 안드로이드 스튜디오
  
- SQLite


## 1. 설명
  ### 1) 기술개발의 목적 및 중요성
  : 본 개발은 학생과 교수간의 원활한 의사소통을 위한 어플로 학생과 교수간의 약속에 있어 소요되는 시간을 줄이고자 한다.
  : 해당 어플을 통해 학생은 장문의 메일없이 간편하게 교수님께 약속을 요청하고 교수님은 어플로 약속 승인 취소는 물론 일정관리도 가능하다.
 
  ### 2) 기능
  
  ○ 학생 - 교수님을 검색하여 약속 요청하기
  
  ○ 교수 - 학생에게 약속 요청이 오면 알림이 오고, 어플을 이용하여 약속 승인 취소가 가능  
  
  ○ 학생, 교수 - 자신의 일정관리
  <br/>
  
  ### 3) 사용설명서
 <img src="https://postfiles.pstatic.net/MjAyMDA1MjdfNzQg/MDAxNTkwNTg2ODUzNDgy.c0bEa_NvXwWdqjFHlfFmKHWZL2S-eQ8C0MIYUTDg1Owg.AmY_YRr3zBdylxTu2OPZCogAwXltyyxD-GV5t0Qowdwg.JPEG.rmawjdals/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C2.jpeg?type=w580"/>
 <img src="https://postfiles.pstatic.net/MjAyMDA1MjdfMTE4/MDAxNTkwNTg2ODUzNDc4.rzBJUa5lbhzkWUsVnrsLMujEpbyrTauuZUSBimJZ944g.IBy1HnoOJparkBeO-0SxQ1GmrR_LTZhK-cbEUD1bFPog.JPEG.rmawjdals/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C3.jpeg?type=w580" />
 <img src="https://postfiles.pstatic.net/MjAyMDA1MjdfMTI3/MDAxNTkwNTg2ODUzNDky.D2QJZIfG6ILw6Y6rgtwGFMw2uxPIGZUieblqtqJOa2Qg.tfdDE2PWimSg6JKRxlVcUdlETkvPbdrSM1t7dybxgiMg.JPEG.rmawjdals/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C4.jpeg?type=w580"/>
<img src="https://postfiles.pstatic.net/MjAyMDA1MjdfNzMg/MDAxNTkwNTg2ODUzNDg1.JkCb4FxH84Nvte_9ka7HAmje_GeFR5Y3p4Krb6Dstywg.D1HVf_hmmfE6DuvaIQOU7mxpJ8XiK-u_TDpaXD-gIfMg.JPEG.rmawjdals/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C5.jpeg?type=w580"/>
<img src="https://postfiles.pstatic.net/MjAyMDA1MjdfMzAg/MDAxNTkwNTg2ODUzNDkx.KdJbnIdUI3gDkYpjE69j12bJT4hZCTnxIvk1oMMM0pIg.M_U06i26sXrC9aSKdXDMfzb9mQoLsLS6SxnJ2F4Bf_cg.JPEG.rmawjdals/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C6.jpeg?type=w580"/>
<img src="https://postfiles.pstatic.net/MjAyMDA1MjdfMjI0/MDAxNTkwNTg2ODUzNDk0.L5yk_Lqr6D-Oa70iJPlB_Stqjmb0qpyVcLVHftOB9KIg.Up97rYaJVe_XUizphCeH6lY0qc_5ZpefrFxG6ceQuyMg.JPEG.rmawjdals/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C7.jpeg?type=w580"/>
<img src="https://postfiles.pstatic.net/MjAyMDA1MjdfMTMg/MDAxNTkwNTg2ODUzNzc1.tN4clah0T4lrsvcihkfHd7nREBxvtF1YQXT3f3QX0Uog.YVBMlmP46rzLXsNvKXqyJ9E_c7No3fepDDhWcYAS0-sg.JPEG.rmawjdals/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C8.jpeg?type=w580"/>
<img src="https://postfiles.pstatic.net/MjAyMDA1MjdfMTU4/MDAxNTkwNTg2ODUzNzgy.-Sjo4IX7N27EX253uH7vnLcvc7koH8G6vrg1WQkc8xog.lIVVqyCiCqMzxZgXYfv7O3tkOHLicyj4YWJh3Co1Jy8g.JPEG.rmawjdals/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C9.jpeg?type=w580"/>
<img src="https://postfiles.pstatic.net/MjAyMDA1MjdfMTAy/MDAxNTkwNTg2ODUzNzkw.gP09jkfVJ_VMyVHdL5KYIMba2Nz9CgyPumMfQB3Vvlog.qPxWuwyvUYR0J8R3pqazdOOjPS6jIprc36cWEEOJ-Dsg.JPEG.rmawjdals/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C10.jpeg?type=w580"/>
<img src="https://postfiles.pstatic.net/MjAyMDA1MjdfMTMg/MDAxNTkwNTg2ODUzODE1.eeoO04GkS7q0IgoFI4UpV2IHSGYQUpQ_mxLPum33tJQg.vAic56vXS4kAPKMfOBaz5oxy6gXM0kjb2MbbDUZXKLEg.JPEG.rmawjdals/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C11.jpeg?type=w580"/>
<img src="https://postfiles.pstatic.net/MjAyMDA1MjdfODIg/MDAxNTkwNTg2ODUzODQx.LYfOSpQGd2wabXj-BOKpV6R7AgMbBeo32PdI9q7XiTsg.OsbKqVGizmeRem0DjL4r7_crpVNnf1lKHPGtftNUnrAg.JPEG.rmawjdals/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C12.jpeg?type=w580"/>
<img src="https://postfiles.pstatic.net/MjAyMDA1MjdfMjA2/MDAxNTkwNTg2ODUzODU3.ort_lMeezo-r0yCur4JvqWmkhcDk3BrB0Ct1eZfxOz8g.Ye0xvjB7jp_MF4mQEbqE84hFSbpMZVTqs3fpnqfMLOwg.JPEG.rmawjdals/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C13.jpeg?type=w580"/>
<img src="https://postfiles.pstatic.net/MjAyMDA1MjdfMjkg/MDAxNTkwNTg2ODU0MTM0.QI1L4776s7chumCxeVJGn4otHuNtmSsugD5iikc3spYg.f6ozM2Yzb8j7FU0j0PYp8lYjpg9I1e8XrG150BLDqpcg.JPEG.rmawjdals/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C14.jpeg?type=w580" />

<br/>

## 3. 프론트 시나리오
  ### 1) 초기 화면 구상도
  - 아래 이미지를 클릭하면 프론트 시나리오 흐름을 볼 수 있습니다.
 <a href="https://xd.adobe.com/view/9d5251ca-726d-431b-4bf3-cf652c38ddf8-eb63/"><img src="https://postfiles.pstatic.net/MjAyMDA1MjdfMTc0/MDAxNTkwNTg3MTM1NDc5.W6mLfHwFW9c3yrdWcM44QmrHy69-LfKxyw75nQcRM6gg.koqtsRlV6FtuI7Ch8AWjRZBuMIYZubD0oPuC8Bh-VF0g.PNG.rmawjdals/8._%EC%8A%A4%EC%BC%80%EC%A5%B4_%EC%96%B4%ED%94%8C_%EB%94%94%EC%9E%90%EC%9D%B8.png?type=w580"/></a>
  
<br/>

## 4. 시연 영상
[![Video Label](https://postfiles.pstatic.net/MjAyMDA1MjdfMjI5/MDAxNTkwNTg2MzU2NjUy.3OVLLP1h3A3WPSUd4mAenZuq_dEE6k6RwnR6R_DNF1Ig.n7oI9e4gAI01TJAPSTROa552Mwt9sWoT-G4Up6kDIlcg.PNG.rmawjdals/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2020-05-27_%EC%98%A4%ED%9B%84_10.20.16.png?type=w580)](https://youtu.be/u5ijEO8pZew)
