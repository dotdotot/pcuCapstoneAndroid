# 안드로이드 캡스톤

ui 디자인 및 기능 추가 (7/23 ~ )

- 초기 구상

   필요한 기능들이 무엇인지 정리 후, 각 기능들을 사용자가 보기 쉽도록 기능별 화면을 디자인하였습니다.
  ![디자인1](https://user-images.githubusercontent.com/72554589/190897369-7e189c25-ebe5-4056-b887-a929a5d835c2.jpg)
![디자인2](https://user-images.githubusercontent.com/72554589/190897371-694af79f-14e0-4d26-84c0-032b8bba2398.jpg)
![디자인3](https://user-images.githubusercontent.com/72554589/190897376-ecd7f183-8171-48a6-bf89-509b8bd8b6a8.jpg)

- 코드 작성

  먼저 디자인했던 화면들을 먼저 구상하였고, 후에 화면별 세세한 기능들을 추가함으로써 서버만 연결이 되면 문제없이 작동하도록 작성하였습니다.
    
    
  - 홈 화면
  
    현재 청소기 위치에 따른 정보들을 바로 보이도록 하였습니다.
    <img width="264" alt="화면-홈" src="https://user-images.githubusercontent.com/72554589/190898862-221b5a1a-e752-4650-947a-55eba72c2816.png">
    
  - 이동 화면
  
    청소기의 이동 방식을 설정하는 화면입니다. 청소기가 자동으로 움직일지, 사용자가 설정한 시간별로 움직일지, 사용자가 설정한 장소로 움직일지 설정하는 것입니다.
    <img width="264" alt="화면-이동1" src="https://user-images.githubusercontent.com/72554589/190898856-3a6fee3a-71ef-4d5d-bbfa-2adc88075fb2.png">
<img width="260" alt="화면-이동2" src="https://user-images.githubusercontent.com/72554589/190898857-680fd18a-d28d-4ad1-8af8-374568858535.png">
<img width="265" alt="화면-이동3" src="https://user-images.githubusercontent.com/72554589/190898858-4981b534-6e87-40d5-85cc-fe1468a36055.png">    

  - 통계 화면
  
    사용자가 원하는 기간동안의 평균적인 수치를 보여주는 화면입니다. 달력 이미지를 눌러 나오는 캘린더뷰로 날짜를 고를 수 있도록 하였습니다.
    <img width="264" alt="화면-통계1" src="https://user-images.githubusercontent.com/72554589/190898859-eee1f94c-fb5a-4614-89b4-45cfb61d3996.png">
<img width="264" alt="화면-통계2" src="https://user-images.githubusercontent.com/72554589/190898861-efe0639c-7689-4125-8039-614cd31899b2.png">

  - 설정 화면
  
    아직까진 로그아웃밖에 기능을 넣지 못했으나, 더 추가할 것이 생긴다면 추가할 계획입니다.
    <img width="263" alt="화면-설정" src="https://user-images.githubusercontent.com/72554589/190898855-b9467837-3006-4519-9e8a-d62507442cb3.png">
   
  - 로그인
  
   화면 구성을 끝낸 후 로그인 기능을 추가하여 처음 시작이 로그인 화면이 되도록 설정하였습니다. 후에 회원가입 기능도 추가하였습니다.
   지금은 데이터베이스를 연결하지 않아 회원 정보가 저장이 되진 않습니다.
   <img width="260" alt="화면-로그인" src="https://user-images.githubusercontent.com/72554589/190898854-0ab1f02b-6973-4d18-992d-8fa345b31010.png">
   <img width="261" alt="화면-회원가입1" src="https://user-images.githubusercontent.com/72554589/190898863-3b574887-c39c-496c-8f57-ec738dbc3b75.png">
<img width="263" alt="화면-회원가입2" src="https://user-images.githubusercontent.com/72554589/190898864-81cee53d-d3e4-45c0-a04f-f3fb090ea0f3.png">


- 어려웠던 점 ( ~ 9/18 )
 
   하단 네비게이션바를 추가할 때 처음 추가해보는 기능이라 시간이 꽤 들었던 부분입니다. 또한 로그인화면에서 회원가입화면과 홈화면으로 넘어가는 과정도 처음 사용해보는 코드를 이용해야 했어서 어려웠습니다. 하지만 여러 블로그를 참고하여 잘 해결하였습니다.
   
 - 참고 주소
 ```
 네비게이션바 추가: https://aries574.tistory.com/149
 화면 이동: https://deumdroid.tistory.com/entry/%EB%B2%84%ED%8A%BC-%ED%81%B4%EB%A6%AD%EC%9C%BC%EB%A1%9C-%ED%94%84%EB%9E%98%EA%B7%B8%EB%A8%BC%ED%8A%B8-%EB%9D%84%EC%9A%B0%EA%B8%B0
 이미지 출처: https://www.flaticon.com/kr/
 ```
