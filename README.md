 # Unit Test 
  ## viewModel
  測試商業邏輯案例
  ## liveData local unit tests
  - 使用 JUnit 測試規則 InstantTaskExecutorRule 同步執行每個任務的不同背景執行程式
  - 新增 LiveDataTestUtil.kt Class 使用 getOrAwaitValues 等待執行對 liveData.postValue、setValue，測試最終提供給View的資料驗證
  - 新增 single event，測試一次事件的邏輯 
  ## Repository
  - **Make a Fake Data Source**
    - 新增 FakeDataSource 實作interface DataSource，測試 RemoteDataSource、LocalDataSourc 模擬回傳的資料 
  - **Write a Test Using Dependency Injection**
    - Repository Constructor Dependency Injection 實作 interface DataSource的Remote或local
  - **Task: Set up a Fake Repository**
    - 當FakeImagesRepositoryImpl時使用FakeDataSource替代原Constructor Dependency Injection，測試資料來源 Remote、Local 是否正確
  - **Task: Use the Fake Repository inside a ViewModel**
    - FakeImagesRepositoryImpl 實作 interface Repository，仿造真實的實作，提供測試 ViewModel  Constructor Dependency Injection FakeRepository

# library 
  - **Model** : Retrofit、OKhttp 
  - **View** : glide、recyclerview
  - **ViewMode** : viewModel、liveData、coroutine

# unit tests
local unit tests、JVM testing、Instrumented testing、 coroutine test

# Design Patter 
Service Locter 
