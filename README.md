# Miracle-Diary
<img src="/readmeImg/ic_launcher.png" width="100px" height="100px" title="px(픽셀) 크기 설정" alt="a"></img> <br>
습관과 할 일을 정리하고 레벨시스템을 통해 자기개발을 돕고 성장의 정도를 볼 수 있는 게임형 다이어리 플랫폼입니다.

## 컴퓨터 구성 / 필수 조건 (Prerequisites)
Android Version : at least Android API Level 26 (Oreo)

## 설치 (Installation Process)
<a href="https://github.com/ounols/Miracle-Diary/blob/master/MiracleDiary.apk?raw=true">미라클 다이어리 다운로드</a>


## 사용법 (Getting Started)

<img src="/readmeImg/메인화면1.png" width="209px" height="437px" title="px(픽셀) 크기 설정" alt="a"></img>
<img src="/readmeImg/습관.PNG" width="209px" height="437px" title="px(픽셀) 크기 설정" alt="a"></img>

첫 번째 탭을 눌러서 중요한 일 혹은 반복할 일(길들일 습관)을 입력합니다.

<img src="/readmeImg/메인화면2.png" width="209px" height="437px" title="px(픽셀) 크기 설정" alt="a"></img>
<img src="/readmeImg/일기장.PNG" width="209px" height="437px" title="px(픽셀) 크기 설정" alt="a"></img>

두 번째 탭을 눌러 일기를 작성합니다. 일기 갯수에 따라 메인의 레벨이 결정됩니다.
(일기 작성 전, 과거 일기를 상기시켜줍니다.)

<img src="/readmeImg/메인화면3.png" width="209px" height="437px" title="px(픽셀) 크기 설정" alt="a"></img>
<img src="/readmeImg/알람.PNG" width="209px" height="437px" title="px(픽셀) 크기 설정" alt="a"></img>

세 번째 탭을 눌러 알람을 설정합니다. 오늘 일기 작성, 중요한 일, 반복할 일 등 주기적으로 체크할 알람을 설정해줍니다.
(레벨에 따라 등록할 수 있는 갯수에 제한이 있습니다.)

<img src="/readmeImg/메인화면4.png" width="209px" height="437px" title="px(픽셀) 크기 설정" alt="a"></img>
<img src="/readmeImg/캘린더.PNG" width="209px" height="437px" title="px(픽셀) 크기 설정" alt="a"></img>

네 번째 탭을 눌러 기록한 일기들을 확인합니다. 얼마나 일기를 꾸준하게 썼는지, 언제 어떤 일기를 썼는지.

<img src="/readmeImg/알림1.PNG" width="209px" height="437px" title="px(픽셀) 크기 설정" alt="a"></img>
<img src="/readmeImg/알림2.PNG" width="209px" height="437px" title="px(픽셀) 크기 설정" alt="a"></img>

설정한 알림을 받으면 습관에 관한 명언을 함께 볼 수 있습니다.

## 파일 정보 및 목록 (File Manifest)
AlarmReceiver.java
BaseCustomBarActivity.java
CalendarActivity.java
DBHelper.java
DBManager.java
DeviceBootReceiver.java
DiaryActivity.java
DiaryFragment.java
EditorActivity.java
EditorTextWatcher.java
EventDecorator.java
GoalActivity.java
GoalSimpleAdapter.java
HighlightSimpleAdapter.java
MainActivity.java
MenuActivity.java
Singleton.java
SplashActivity.java
TextHighlightChanger.java

EditorTutorialDialog.java
HabitEditorDialog.java

actionbar_default.xml
actionbar_editor.xml	
actionbar_prev.xml
actionbar_settime.xml
activity_diary.xml
activity_editor.xml
activity_goal.xml
activity_main.xml
calendar_layout.xml
content_diary.xml
content_goal.xml
dialog_editor_tutorial.xml
dialog_habit_editor.xml
menu.xml
row_cal.xml
row_diary.xml
row_goal.xml
splash_intro.xml

## 저작권 및 사용권 (Copyright / End User License)

MIT License

Copyright (c) 2019 Miracle-Diary

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


## 배포자 및 개발자의 연락처 정보 (Contact Information)

이준호 : lhsljh456@naver.com

이진영 : asdfounols@naver.com

차재명 : maxcha98@naver.com

정홍주 : sub06038@naver.com

## 알려진 버그 (Known Issues)

캘린더에서 다이어리_날짜 로딩 시간이 1초이상 걸림

## 문제 발생에 대한 해결책 (Troubleshooting)

코드의 리팩토링과 DB의 최적화

개발자 이메일로 버그내용을 보내주시기 바랍니다.

## 크레딧 (Credit)

Material Calendar View is Copyright (c) 2018 Prolific Interactive. It may be redistributed under the terms specified in the LICENSE file.//https://github.com/prolificinteractive/material-calendarview

## 업데이트 정보 (Change Log)

version-alpha (2019/10/24)
